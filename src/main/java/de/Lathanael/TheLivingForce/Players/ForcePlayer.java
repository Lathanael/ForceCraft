package de.Lathanael.TheLivingForce.Players;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.Lathanael.TheLivingForce.Enums.ForceAlignment;
import de.Lathanael.TheLivingForce.Enums.Ranks;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

public class ForcePlayer {
	protected String name;
	protected ForceAlignment alignment = ForceAlignment.NEUTRAL;
	protected Player handler;
	protected Ranks rank = Ranks.NONE;
	protected FileConfiguration playerConfig;
	protected int count = 0;
	protected File playerFile;

	public ForcePlayer (String playerName, String dir) {
		this.name = playerName;
		this.handler = ForcePlugin.getInstance().getServer().getPlayer(playerName);
		initilize(dir);
	}

	public ForcePlayer (Player player, String dir) {
		this.name = player.getName();
		this.handler = player;
		initilize(dir);
	}

	public ForcePlayer (String name) {
		this.name = name;
		this.handler = ForcePlugin.getInstance().getServer().getPlayer(name);
		initilize(ForcePlugin.getInstance().getDataFolder()	+ File.separator + "players");
	}

	private void initilize(String dir) {
		this.playerFile = new File(dir, name + ".yml");
		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				playerConfig.addDefault("Key.E", "none");
				playerConfig.addDefault("Key.F", "none");
				playerConfig.addDefault("Key.R", "none");
				playerConfig.options().copyDefaults(true);
				playerConfig.save(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		}
	}

	public void save() {
		try {
			playerConfig.save(playerFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateFile() {
		if (count == 5) {
			try {
				playerConfig.save(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			count = 0;
		}
	}

	public void setRank(Ranks rank) {
		playerConfig.set("Rank", rank);
		count++;
		updateFile();
	}

	public Ranks getRank() {
		return (Ranks) playerConfig.get("Rank", Ranks.NONE);
	}

	public void setAlignment(ForceAlignment fa) {
		playerConfig.set("Alignment", fa);
		count++;
		updateFile();
	}

	public ForceAlignment getAligment() {
		return (ForceAlignment) playerConfig.get("Alignment", ForceAlignment.NEUTRAL);
	}

	// TODO: Better way to handle more than 3 keys!
	public void setKey(String key, String power) {
		ConfigurationSection keys = playerConfig.getConfigurationSection("Key");
		if (keys.contains(key))
			playerConfig.set("Key." + key, power);
		else
			if (keys.getKeys(false).size() < 3)
				playerConfig.set("Key." + key, power);
			else {
				Set<String> temp = keys.getKeys(false);
				int i = 1;
				for (String string : temp)
					if (i == 1)
						string = key;
			}
	}

	public void getKey() {

	}
}
