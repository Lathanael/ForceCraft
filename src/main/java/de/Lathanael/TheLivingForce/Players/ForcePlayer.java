package de.Lathanael.TheLivingForce.Players;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.keyboard.Keyboard;

import de.Lathanael.TheLivingForce.Utils.ForceAlignment;
import de.Lathanael.TheLivingForce.Utils.Ranks;
import de.Lathanael.TheLivingForce.Utils.SpoutKeys;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ForcePlayer {
	protected String name;
	protected ForceAlignment alignment = ForceAlignment.NEUTRAL;
	protected Player handler;
	protected Ranks rank = Ranks.NONE;
	protected FileConfiguration playerConfig;
	protected int count = 0;
	protected File playerFile;
	protected HashMap<Keyboard, String> keys = new HashMap<Keyboard, String>();

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
				playerConfig.addDefault("Alignment", ForceAlignment.NEUTRAL);
				playerConfig.addDefault("Rank", Ranks.NONE);
				playerConfig.addDefault("Keys", Arrays.asList("18.none", "33.none", "19.none"));
				playerConfig.options().copyDefaults(true);
				playerConfig.save(playerFile);
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			rank = (Ranks) playerConfig.get("Rank");
			alignment = (ForceAlignment) playerConfig.get("Alignment");
			@SuppressWarnings("unchecked")
			List<String> tempKeys = playerConfig.getList("Keys");
			if (tempKeys.size() > 3)
				tempKeys = tempKeys.subList(0, 4);
			for (String key : tempKeys) {
				String splittedKeys[] = key.split(".");
				if (splittedKeys.length >= 2)
					keys.put(SpoutKeys.getKey(Integer.valueOf(splittedKeys[0])), splittedKeys[1]);
			}
			tempKeys.clear();
		}
	}

	public void updateFile(boolean forceSave) {
		if (count == 5 || forceSave) {
			try {
				playerConfig.save(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			count = 0;
		}
		else
			count++;
	}

	public void updateFile() {
		updateFile(false);
	}

	public void setRank(int rank) {
		playerConfig.set("Rank", rank);
		updateFile();
	}

	public int getRank() {
		return ((Ranks) playerConfig.get("Rank", Ranks.NONE)).getRank();
	}

	public void setAlignment(ForceAlignment fa) {
		playerConfig.set("Alignment", fa);
		updateFile();
	}

	public ForceAlignment getAligment() {
		return (ForceAlignment) playerConfig.get("Alignment", ForceAlignment.NEUTRAL);
	}

	public void setKey(Keyboard key, String power) {
		Iterator<Map.Entry<Keyboard, String>> entries = keys.entrySet().iterator();
		int i = 1;
		while (entries.hasNext()) {
				Map.Entry<Keyboard, String> entry = entries.next();
				if (entry.getKey() == key)
					entry.setValue(power);
				else {
					if (i == 1) {
						i = 0;
						entries.remove();
						keys.put(key, power);
					}
				}
		}
		updateFile(true);
	}

	public boolean containsKey(String key) {
		return keys.containsKey(key);
	}

	public void updateKeysInFile() {
		List<String> temp = new ArrayList<String>();
		for(Map.Entry<Keyboard, String> entries : keys.entrySet()) {
			String key = String.valueOf(entries.getKey().getKeyCode()).concat(".").concat(entries.getValue());
			temp.add(key);
		}
		playerConfig.set("Keys", temp);
	}
}