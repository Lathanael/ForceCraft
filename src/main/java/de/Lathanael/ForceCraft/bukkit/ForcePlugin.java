/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.Lathanael.ForceCraft.Commands.BindKey;
import de.Lathanael.ForceCraft.Commands.CommandsHandler;
import de.Lathanael.ForceCraft.Commands.Info;
import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Commands.Set;
import de.Lathanael.ForceCraft.Listeners.FCEntityListener;
import de.Lathanael.ForceCraft.Listeners.FCInputListener;
import de.Lathanael.ForceCraft.Listeners.FCPlayerListener;
import de.Lathanael.ForceCraft.Listeners.FCPluginListener;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.Pull;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ForcePlugin extends JavaPlugin {

	private FCPlayerListener fcPL;
	private FCPluginListener fcPluL;
	private FCEntityListener fcEL;
	private static ForcePlugin instance;
	public FileConfiguration config;
	public FileConfiguration ranksInfo;
	public String directory;
	public CommandsHandler commandsHandler;
	public static Logger log = Logger.getLogger("ForceCraft");
	public PluginManager pm;
	public static boolean debug = false;
	public static boolean sensitiveonJoin = false;
	private static HashMap<UUID, LivingEntity> entitiesStroked = new HashMap<UUID, LivingEntity>();

	public void onDisable() {
		Tools.savePlayerFiles(PlayerHandler.getInstance().getPlayerList());
		PluginDescriptionFile pdf = getDescription();
		log.info("[" + pdf.getName() + "] Version " + pdf.getVersion() + " disabled!");
	}

	public void onEnable() {
		instance = this;
		directory = getDataFolder().getPath() + File.separator + "config.yml";
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		loadConfig(config);
		loadRanksInfo();
		PlayerHandler.setInstance();
		PlayerHandler.getInstance().initialize(getDataFolder().getPath());
		fcPL = new FCPlayerListener();
		fcPluL = new FCPluginListener();
		fcEL = new FCEntityListener();
		commandsHandler = CommandsHandler.initInstance(this);
		registerCommands();
		PermissionsHandler.setInstance();
		Scheduler.initInstance(this);
		fcPluL.hook();
		pm = getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, fcPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_QUIT, fcPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_KICK, fcPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_MOVE, fcPL, Priority.Normal, this);
		pm.registerEvent(Type.CUSTOM_EVENT, new FCInputListener(this), Priority.Normal, this);
		pm.registerEvent(Type.PLUGIN_DISABLE, fcPluL, Priority.Normal, this);
		pm.registerEvent(Type.PLUGIN_ENABLE, fcPluL, Priority.Normal, this);
		pm.registerEvent(Type.ENTITY_DAMAGE, fcEL, Priority.Normal, this);
		pm.registerEvent(Type.PROJECTILE_HIT, fcEL, Priority.Normal, this);
		PluginDescriptionFile pdf = getDescription();
		log.info("[" + pdf.getName() + "] Version " + pdf.getVersion() + " enabled!");
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
		sensitiveonJoin = config.getBoolean("ForceSensitiveOnJoin");
	}

	private void loadRanksInfo() {
		File ranksInfoFile = new File(getDataFolder().getPath() + File.separator + "ranksInfo.yml");
		if (!ranksInfoFile.exists()) {
			try {
				ranksInfoFile.createNewFile();
				ranksInfo = YamlConfiguration.loadConfiguration(ranksInfoFile);
				ranksInfo.options().copyDefaults(true);
				ranksInfo.save(ranksInfoFile);
				ranksInfo = YamlConfiguration.loadConfiguration(ranksInfoFile);
			} catch (IOException e) {
				log.info("[ForceCraft] Failed to create ranksInfo.yml!");
				e.printStackTrace();
			}
		} else {
			ranksInfo = YamlConfiguration.loadConfiguration(ranksInfoFile);
		}
	}

	// TODO: registering all commands and powers
	private void registerCommands() {
		if (config.getBoolean("Power.Pull.enabled"))
			commandsHandler.registerPower(Pull.class);
		commandsHandler.registerCommand(Info.class);
		commandsHandler.registerCommand(Set.class);
		commandsHandler.registerCommand(BindKey.class);
	}
}