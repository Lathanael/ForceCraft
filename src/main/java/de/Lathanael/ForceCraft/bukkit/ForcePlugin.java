/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * TheLivingForce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TheLivingForce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TheLivingForce. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.ForceCraft.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.Lathanael.ForceCraft.Commands.CommandsHandler;
import de.Lathanael.ForceCraft.Commands.Info;
import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Listeners.TLFEntityListener;
import de.Lathanael.ForceCraft.Listeners.TLFInputListener;
import de.Lathanael.ForceCraft.Listeners.TLFPlayerListener;
import de.Lathanael.ForceCraft.Listeners.TLFPluginListener;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.Pull;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ForcePlugin extends JavaPlugin {

	private TLFPlayerListener tlfPL;
	private TLFPluginListener tlfPluL;
	private TLFEntityListener tlfEL;
	private static ForcePlugin instance;
	public FileConfiguration config;
	public FileConfiguration ranksInfo;
	public String directory;
	public PlayerHandler playerHandler;
	public CommandsHandler commandsHandler;
	public static Logger log = Logger.getLogger("Minecraft");
	public PluginManager pm;
	public static boolean debug = false;

	public void onDisable() {
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
		tlfPL = new TLFPlayerListener();
		tlfPluL = new TLFPluginListener();
		tlfEL = new TLFEntityListener();
		commandsHandler.initInstance(this);
		registerCommands();
		PermissionsHandler.setInstance();
		tlfPluL.hook();
		pm = getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, tlfPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_QUIT, tlfPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_KICK, tlfPL, Priority.Normal, this);
		pm.registerEvent(Type.CUSTOM_EVENT, new TLFInputListener(this), Priority.Normal, this);
		pm.registerEvent(Type.PLUGIN_DISABLE, tlfPluL, Priority.Normal, this);
		pm.registerEvent(Type.PLUGIN_ENABLE, tlfPluL, Priority.Normal, this);
		pm.registerEvent(Type.ENTITY_DAMAGE, tlfEL, Priority.Normal, this);
		PluginDescriptionFile pdf = getDescription();
		log.info("[" + pdf.getName() + "] Version " + pdf.getVersion() + " enabled!");
	}

	public static ForcePlugin getInstance() {
		return instance;
	}

	private void loadConfig(FileConfiguration config) {
		debug = config.getBoolean("DebugMessages");
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
				log.info("[TheLivingForce] Failed to create ranksInfo.yml!");
				e.printStackTrace();
			}
		} else {
			ranksInfo = YamlConfiguration.loadConfiguration(ranksInfoFile);
		}
	}

	private void registerCommands() {
		if (config.getBoolean("Power.Pull.enabled"))
			commandsHandler.registerPower(Pull.class);
		commandsHandler.registerCommand(Info.class);
	}
}