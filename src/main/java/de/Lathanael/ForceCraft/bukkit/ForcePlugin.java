/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
 *
 * ForceCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ForceCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ForceCraft. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.ForceCraft.bukkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.keyboard.Keyboard;

import de.Lathanael.ForceCraft.Commands.BindKey;
import de.Lathanael.ForceCraft.Commands.CommandsHandler;
import de.Lathanael.ForceCraft.Commands.Create;
import de.Lathanael.ForceCraft.Commands.Demote;
import de.Lathanael.ForceCraft.Commands.Info;
import de.Lathanael.ForceCraft.Commands.Join;
import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Commands.Promote;
import de.Lathanael.ForceCraft.Commands.Reload;
import de.Lathanael.ForceCraft.Commands.Set;
import de.Lathanael.ForceCraft.Events.FCKeyBinding;
import de.Lathanael.ForceCraft.Listeners.FCButtonListener;
import de.Lathanael.ForceCraft.Listeners.FCEntityListener;
import de.Lathanael.ForceCraft.Listeners.FCInputListener;
import de.Lathanael.ForceCraft.Listeners.FCPlayerListener;
import de.Lathanael.ForceCraft.Listeners.FCPluginListener;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.Choke;
import de.Lathanael.ForceCraft.Powers.Flash;
import de.Lathanael.ForceCraft.Powers.Heal;
import de.Lathanael.ForceCraft.Powers.Jump;
import de.Lathanael.ForceCraft.Powers.Lift;
import de.Lathanael.ForceCraft.Powers.Lightning;
import de.Lathanael.ForceCraft.Powers.Meditation;
import de.Lathanael.ForceCraft.Powers.Pull;
import de.Lathanael.ForceCraft.Powers.Push;
import de.Lathanael.ForceCraft.Powers.Rage;
import de.Lathanael.ForceCraft.Powers.Run;
import de.Lathanael.ForceCraft.Powers.Shield;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ForcePlugin extends JavaPlugin {

	private FCPluginListener fcPluL = new FCPluginListener();
	private static ForcePlugin instance;
	public FileConfiguration config;
	public FileConfiguration powerInfo;
	public FileConfiguration texturePaths;
	public FileConfiguration autoPromoteValues;
	public String directory;
	public CommandsHandler commandsHandler;
	public static Logger log;
	public PluginManager pm;
	public static boolean debug = false;
	public static boolean sensitiveonJoin = false;
	public static boolean autoPromote = true;
	private static HashMap<UUID, LivingEntity> entitiesStroked = new HashMap<UUID, LivingEntity>();
	public static int checkDist = 0;
	public static String manaBarTexURL = "";
	public static String backgroundTexURL = "";
	public static String skillPointTexURL = "";
	public static String spHighlightedTexURL = "";
	public static int maxSP;
	public static int startingSP;
	public static boolean manaBarEnabled;
	private boolean replace = false;

	public void onDisable() {
		Tools.savePlayerFiles(PlayerHandler.getInstance().getPlayerList());
		saveConfig();
		try {
			powerInfo.save(new File(getDataFolder().getPath() + File.separator + "powerInfo.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PluginDescriptionFile pdf = getDescription();
		log.info("Version " + pdf.getVersion() + " disabled!");
	}

	public void onEnable() {
		instance = this;
		directory = getDataFolder().getPath() + File.separator + "config.yml";
		log = getLogger();
		config = YamlConfiguration.loadConfiguration(loadConfigurationFile());
		saveConfig();
		loadConfig(config);
		loadPowerInfoFile();
		loadTexturePathsFile();
		loadTextures();
		loadAutoPromoteFile();
		PromoteValues.setInstance();
		PromoteValues.loadValues(autoPromoteValues);
		PlayerHandler.setInstance();
		PlayerHandler.getInstance().initialize(getDataFolder().getPath());
		commandsHandler = CommandsHandler.initInstance(this);
		registerCommands();
		PermissionsHandler.setInstance();
		Scheduler.initInstance(this);
		fcPluL.hook();
		pm = getServer().getPluginManager();
		pm.registerEvents(new FCPlayerListener(), this);
		pm.registerEvents(new FCInputListener(this), this);
		pm.registerEvents(new FCPluginListener(), this);
		pm.registerEvents(new FCEntityListener(), this);
		pm.registerEvents(new FCButtonListener(this), this);
		PluginDescriptionFile pdf = getDescription();
		try {
			SpoutManager.getKeyBindingManager().registerBinding("ForceCraft GUI", Keyboard.KEY_M, "Open ForceCraft GUI", new FCKeyBinding(), this);
		} catch(IllegalArgumentException e) {
			Tools.debugMsg("Binding already registered!", null);
		}
		log.info("Version " + pdf.getVersion() + " enabled!");
	}

	public static ForcePlugin getInstance() {
		return instance;
	}

	public static void setStrokedEntity(LivingEntity entity) {
		entitiesStroked.put(entity.getUniqueId(), entity);
	}

	public  static LivingEntity getStrokedEntity(UUID entityID) {
		if (entitiesStroked.containsKey(entityID))
			return entitiesStroked.get(entityID);
		return null;
	}

	public static void removeStrokedEntity(UUID entityID) {
		if (entitiesStroked.containsKey(entityID))
			entitiesStroked.remove(entityID);
	}


	public static boolean containsStrokedEntity(UUID uniqueId) {
		return entitiesStroked.containsKey(uniqueId);
	}

	private void loadConfig(FileConfiguration config) {
		debug = config.getBoolean("DebugMessages");
		sensitiveonJoin = config.getBoolean("ForceSensitiveOnJoin", false);
		checkDist = config.getInt("checkDistance", 20);
		maxSP = config.getInt("maxSkillPoints", 20);
		startingSP = config.getInt("startingSkillPoints", 10);
		manaBarEnabled = config.getBoolean("manaBarEnabled", true);
		autoPromote = config.getBoolean("EnableAutoPromote", true);
	}

	public void loadTextures() {
		manaBarTexURL = texturePaths.getString("manaBarTexURL", "http://dl.dropbox.com/u/42731731/BarTex.jpg");
		skillPointTexURL = texturePaths.getString("SkillPoint", "http://dl.dropbox.com/u/42731731/SkillPoint.png");
		spHighlightedTexURL = texturePaths.getString("SPHighlighted", "http://dl.dropbox.com/u/42731731/SkillPointHighlighted.png");
		backgroundTexURL = texturePaths.getString("BackgroundTexURL", "http://dl.dropbox.com/u/42731731/Background.png");
	}

	private void loadPowerInfoFile() {
		File file = new File(getDataFolder().getPath() + File.separator + "powerInfo.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				InputStream in = getResource("powerInfo.yml");
				FileWriter writer = new FileWriter(file);
				for (int i = 0; (i = in.read()) > 0;) {
					writer.write(i);
				}
				writer.flush();
				writer.close();
				in.close();
				powerInfo = YamlConfiguration.loadConfiguration(file);
				powerInfo.save(file);
			} catch (IOException e) {
				log.info("Failed to create powerInfo.yml!");
				e.printStackTrace();
			}
		} else {
			powerInfo = YamlConfiguration.loadConfiguration(file);
		}
	}

	private void loadAutoPromoteFile() {
		File file = new File(getDataFolder().getPath() + File.separator + "autoPromoteValues.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				InputStream in = getResource("autoPromoteValues.yml");
				FileWriter writer = new FileWriter(file);
				for (int i = 0; (i = in.read()) > 0;) {
					writer.write(i);
				}
				writer.flush();
				writer.close();
				in.close();
				autoPromoteValues = YamlConfiguration.loadConfiguration(file);
				autoPromoteValues.save(file);
			} catch (IOException e) {
				log.info("Failed to create autoPromoteValues.yml!");
				e.printStackTrace();
			}
		} else {
			autoPromoteValues = YamlConfiguration.loadConfiguration(file);
		}
	}

	private void loadTexturePathsFile() {
		File file = new File(getDataFolder().getPath() + File.separator + "texturePaths.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				InputStream in = getResource("texturePaths.yml");
				FileWriter writer = new FileWriter(file);
				for (int i = 0; (i = in.read()) > 0;) {
					writer.write(i);
				}
				writer.flush();
				writer.close();
				in.close();
				texturePaths = YamlConfiguration.loadConfiguration(file);
				texturePaths.save(file);
			} catch (IOException e) {
				log.info("Failed to create texturePaths.yml!");
				e.printStackTrace();
			}
		} else {
			texturePaths = YamlConfiguration.loadConfiguration(file);
		}
	}

	/**
	 * @author Balor (aka Antoine Aflalo)
	 * @author Lathanael (aka Philippe Leipold)
	 */
	private File loadConfigurationFile() {
		String fileVersion = null;
		try {
			Properties gitVersion = new Properties();
			gitVersion.load(getResource("git.properties"));
			fileVersion = (String) gitVersion.get("git.commit.id");
			Tools.debugMsg("Git Version : " + fileVersion, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File folder = new File(getDataFolder().getPath());
		File file = new File(getDataFolder().getPath() + File.separator + "config.yml");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
			}

			try {
				String version = reader.readLine();
				final String versioncheck = version.substring(9);
				if (!versioncheck.equals(fileVersion) && replace) {
					reader.close();
					file.delete();
					log.info("Version mismatch in config.yml, deleteing file: " + file);
				} else
					return file;
			} catch (IOException e) {
				file.delete();
			}

			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		if (!file.exists()) {
			final InputStream res = getResource("config.yml");
			FileWriter tx = null;
			try {
				tx = new FileWriter(file);
				for (int i = 0; (i = res.read()) > 0;) {
					tx.write(i);
				}
				tx.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
					return file;
				} finally {
					try {
						res.close();
					} catch (Exception ex) {
					}

					try {
						if (tx != null) {
							tx.close();
						}
					} catch (Exception ex) {
					}
				}
		}
		return file;
	}

	public void reload() {
		loadPowerInfoFile();
		loadTexturePathsFile();
		loadTextures();
		loadAutoPromoteFile();
		config = YamlConfiguration.loadConfiguration(loadConfigurationFile());
		loadConfig(config);
	}

	private void registerCommands() {
		// Register powers if they are enabled
		if (config.getBoolean("Power.Pull.enabled")) {
			commandsHandler.registerPower(Pull.class);
			Tools.debugMsg("Power Pull enabled", null);
		} else
			Tools.debugMsg("Power Pull disabled", null);
		if (config.getBoolean("Power.Meditation.enabled")) {
			commandsHandler.registerPower(Meditation.class);
			Tools.debugMsg("Power Meditation enabled", null);
		} else
			Tools.debugMsg("Power Meditation disabled", null);
		if (config.getBoolean("Power.Push.enabled")) {
			commandsHandler.registerPower(Push.class);
			Tools.debugMsg("Power Push enabled", null);
		} else
			Tools.debugMsg("Power Push disabled", null);
		if (config.getBoolean("Power.Lift.enabled")) {
			commandsHandler.registerPower(Lift.class);
			Tools.debugMsg("Power Lift enabled", null);
		} else
			Tools.debugMsg("Power Lift disabled", null);
		if (config.getBoolean("Power.Jump.enabled")) {
			commandsHandler.registerPower(Jump.class);
			Tools.debugMsg("Power Jump enabled", null);
		} else
			Tools.debugMsg("Power Jump disabled", null);
		if (config.getBoolean("Power.Rage.enabled")) {
			commandsHandler.registerPower(Rage.class);
			Tools.debugMsg("Power Rage enabled", null);
		} else
			Tools.debugMsg("Power rage disabled", null);
		if (config.getBoolean("Power.Shield.enabled")) {
			commandsHandler.registerPower(Shield.class);
			Tools.debugMsg("Power Shield enabled", null);
		} else
			Tools.debugMsg("Power Shield disabled", null);
		if (config.getBoolean("Power.Run.enabled")) {
			commandsHandler.registerPower(Run.class);
			Tools.debugMsg("Power Run enabled", null);
		} else
			Tools.debugMsg("Power Run disabled", null);
		if (config.getBoolean("Power.Heal.enabled")) {
			commandsHandler.registerPower(Heal.class);
			Tools.debugMsg("Power Heal enabled", null);
		} else
			Tools.debugMsg("Power Heal disabled", null);
		if (config.getBoolean("Power.Flash.enabled")) {
			commandsHandler.registerPower(Flash.class);
			Tools.debugMsg("Power Flash enabled", null);
		} else
			Tools.debugMsg("Power Flash disabled", null);
		if (config.getBoolean("Power.Choke.enabled")) {
			commandsHandler.registerPower(Choke.class);
			Tools.debugMsg("Power Choke enabled", null);
		} else
			Tools.debugMsg("Power Choke disabled", null);
		if (config.getBoolean("Power.Lightning.enabled")) {
			commandsHandler.registerPower(Lightning.class);
			Tools.debugMsg("Power Lightning enabled", null);
		} else
			Tools.debugMsg("Power Flash disabled", null);

		// Register commands
		commandsHandler.registerCommand(Info.class);
		commandsHandler.registerCommand(Set.class);
		commandsHandler.registerCommand(BindKey.class);
		commandsHandler.registerCommand(Reload.class);
		commandsHandler.registerCommand(Create.class);
		commandsHandler.registerCommand(Promote.class);
		commandsHandler.registerCommand(Demote.class);
		commandsHandler.registerCommand(Join.class);
	}
}