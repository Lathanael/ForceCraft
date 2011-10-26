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

public class ForcePlugin extends JavaPlugin {

	public TLFPlayerListener tlfPL;
	private static ForcePlugin instance;
	public FileConfiguration config;
	public String directory;
	public static PlayerHandler playerHandler;
	public static CommandsHandler commandsHandler = new CommandsHandler();
	public static Logger log = Logger.getLogger("Minecraft");
	public static PluginManager pm;

	public void onDisable() {
		PluginDescriptionFile pdf = getDescription();
		log.info("[" + pdf.getName() + "] Version " + pdf.getVersion() + " disabled!");
	}

	public void onEnable() {
		instance = this;
		directory = getDataFolder().getPath() + File.separator + "config.yml";
		File file = new File(directory);
		if (!file.exists())
			createConfig();
		else
			config = getConfig();
		PlayerHandler.setInstance();
		PlayerHandler.getInstance().initialize(getDataFolder().getPath());
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

	private void createConfig() {
		config = getConfig();
		config.addDefault("ForceSensitiveOnJoin", false);
		config.addDefault("Power.Push.enabled", true);
		config.addDefault("Power.Push.rank", 1);
		config.addDefault("Power.Pull.enabled", true);
		config.addDefault("Power.Pull.rank", 1);
		config.addDefault("Power.Choke.enabled", true);
		config.addDefault("Power.Choke.rank", 1);
		config.addDefault("Power.lift.enabled", true);
		config.addDefault("Power.lift.rank", 1);
		config.addDefault("Power.Run.enabled", true);
		config.addDefault("Power.Run.rank", 1);
		config.addDefault("Power.Jump.enabled", true);
		config.addDefault("Power.Hump.rank", 1);
		config.addDefault("Power.Lightning.enabled", true);
		config.addDefault("Power.Lightning.rank", 1);
		config.addDefault("Power.Shield.enabled", true);
		config.addDefault("Power.Shield.rank", 1);
		config.addDefault("Power.Flash.enabled", true);
		config.addDefault("Power.Flash.rank", 1);
		config.addDefault("Power.Heal.enabled", true);
		config.addDefault("Power.Heal.rank", 1);
		config.addDefault("Power.Rage.enabled", true);
		config.addDefault("Power.Rage.rank", 1);
		config.addDefault("Power.Mediation.enabled", true);
		config.addDefault("Power.Mediation.rank", 1);
		config.options().copyDefaults(true);
		saveConfig();
	}

	public void registerCommands() {
		getCommand("force").setExecutor(commandsHandler);
	}
}