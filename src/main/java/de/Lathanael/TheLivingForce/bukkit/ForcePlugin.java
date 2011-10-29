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
 * along with SimpleCalc. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.TheLivingForce.bukkit;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.Lathanael.TheLivingForce.Commands.CommandsHandler;
import de.Lathanael.TheLivingForce.Listeners.TLFPlayerListener;
import de.Lathanael.TheLivingForce.Players.PlayerHandler;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ForcePlugin extends JavaPlugin {

	public TLFPlayerListener tlfPL;
	private static ForcePlugin instance;
	public FileConfiguration config;
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
		PlayerHandler.setInstance();
		PlayerHandler.getInstance().initialize(getDataFolder().getPath());
		tlfPL = new TLFPlayerListener();
		commandsHandler = new CommandsHandler(this);
		registerCommands();
		pm = getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, tlfPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_QUIT, tlfPL, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_KICK, tlfPL, Priority.Normal, this);
		PluginDescriptionFile pdf = getDescription();
		log.info("[" + pdf.getName() + "] Version " + pdf.getVersion() + " enabled!");
	}

	public static ForcePlugin getInstance() {
		return instance;
	}

	private void loadConfig(FileConfiguration config) {
		debug = config.getBoolean("DebugMessages");
	}

	public void registerCommands() {
		getCommand("force").setExecutor(commandsHandler);
	}
}