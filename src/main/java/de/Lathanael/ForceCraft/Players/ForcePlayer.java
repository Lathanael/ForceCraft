/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of TheLivingForce.
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

package de.Lathanael.ForceCraft.Players;

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

import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Ranks;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

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
	protected int mana = 0;
	protected File playerFile;
	protected HashMap<Keyboard, String> keys = new HashMap<Keyboard, String>();
	protected HashMap<String, Integer> amounts = new HashMap<String, Integer>();

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
				if (ForcePlugin.debug)
					ForcePlugin.log.info("[TheLivingForce] Creating player file for: " + name);
				playerFile.createNewFile();
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				createDefaults();
				playerConfig.save(playerFile);
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				rank = Ranks.getRank(playerConfig.getInt("Rank"));
				alignment = ForceAlignment.valueOf(((String) playerConfig.get("Alignment")).toUpperCase());
				mana = playerConfig.getInt("Mana");
				loadKeys();
				loadAmounts();
				if (ForcePlugin.debug)
					ForcePlugin.log.info("[TheLivingForce] Player file for " + name + " created!");
			} catch (IOException e) {
				ForcePlugin.log.info("[TheLivingForce] An error occured during the creation of the player file for: " + name);
				e.printStackTrace();
			}
		} else {
			if (ForcePlugin.debug)
				ForcePlugin.log.info("[TheLivingForce] Loading player file for: " + name);
			playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			rank = Ranks.getRank(playerConfig.getInt("Rank"));
			alignment = ForceAlignment.valueOf(((String) playerConfig.get("Alignment")).toUpperCase());
			mana = playerConfig.getInt("Mana");
			loadKeys();
			loadAmounts();
			if (ForcePlugin.debug) {
				ForcePlugin.log.info("[TheLivingForce] Loaded player file for: " + name);
				ForcePlugin.log.info("[TheLivingForce] Loaded attributes are:");
				ForcePlugin.log.info("[TheLivingForce] Alignment: " + alignment.toString());
				ForcePlugin.log.info("[TheLivingForce] Rank: " + rank.toString());
				ForcePlugin.log.info("[TheLivingForce] Keys:");
				for (Map.Entry<Keyboard, String> entries : keys.entrySet()) {
						ForcePlugin.log.info("[TheLivingForce] Key: " + entries.getKey().toString());
						ForcePlugin.log.info("[TheLivingForce] Power: " + entries.getValue());
				}
				ForcePlugin.log.info("[TheLivingForce] Loaded player file for: " + name);
			}
		}
	}

	public void updateFile(boolean forceSave) {
		if (count == 5 || forceSave) {
			try {
				if (ForcePlugin.debug)
					ForcePlugin.log.info("[TheLivingForce] Saving player file for: " + name);
				updateKeysInFile();
				saveAmounts();
				playerConfig.save(playerFile);
			} catch (IOException e) {
				ForcePlugin.log.info("[TheLivingForce] Failed to save player file for: " + name);
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

	public void setRank(Ranks rank) {
		playerConfig.set("Rank", rank.getRankNr());
		this.rank = rank;
		updateFile();
	}

	public int getRank() {
		return rank.getRankNr();
	}

	public String getRankName() {
		return rank.getLabel();
	}

	public void setAlignment(ForceAlignment fa) {
		playerConfig.set("Alignment", fa.toString());
		this.alignment = fa;
		updateFile();
	}

	public ForceAlignment getAlignment() {
		return alignment;
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
		updateKeysInFile();
		updateFile(true);
	}

	public String getKey(Keyboard key) {
		return keys.get(key);
	}

	public boolean containsKey(Keyboard key) {
		return keys.containsKey(key);
	}

	public int getPowerAmount(String power) {
		return amounts.get(power);
	}

	public HashMap<String, Integer> getPowerAmounts() {
		return amounts;
	}

	public void setPowerAmount(String power, int value) {
		amounts.put(power, value);
		updateFile();
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana){
		this.mana = mana;
		updateFile();
	}

	public Player getHandler() {
		return handler;
	}

/*--------------------------------------------------Private functions------------------------------------------------------------------------------*/

	private void createDefaults() {
		playerConfig.addDefault("Alignment", ForceAlignment.NEUTRAL.toString());
		playerConfig.addDefault("Rank", Ranks.NONE.getRankNr());
		playerConfig.addDefault("Keys", Arrays.asList("18.none", "33.none", "19.none"));
		playerConfig.addDefault("Amount.Pull", 0);
		playerConfig.addDefault("Amount.Push", 0);
		playerConfig.addDefault("Amount.Run", 0);
		playerConfig.addDefault("Amount.Jump", 0);
		playerConfig.addDefault("Amount.Heal", 0);
		playerConfig.addDefault("Amount.Lift", 0);
		playerConfig.addDefault("Amount.Choke", 0);
		playerConfig.addDefault("Amount.Rage", 0);
		playerConfig.addDefault("Amount.Flash", 0);
		playerConfig.addDefault("Amount.Mediation", 0);
		playerConfig.addDefault("Amount.Lightning", 0);
		playerConfig.addDefault("Amount.Shield", 0);
		playerConfig.addDefault("Mana", 100);
		playerConfig.options().copyDefaults(true);

	}

	private void loadKeys() {
		@SuppressWarnings("unchecked")
		List<String> tempKeys = playerConfig.getList("Keys");
		if (tempKeys.size() > 3)
			tempKeys = tempKeys.subList(0, 4);
		for (String key : tempKeys) {
			String splittedKeys[] = key.split("\\.");
			if (splittedKeys.length >= 2)
				keys.put(Tools.getKey(Integer.valueOf(splittedKeys[0])), splittedKeys[1]);
		}
	}

	private void loadAmounts() {
		Map<String, Object> tempList = playerConfig.getConfigurationSection("Amount").getValues(false);
		for (Map.Entry<String, Object> entries : tempList.entrySet()) {
			amounts.put(entries.getKey(), (Integer) entries.getValue());
		}
	}

	private void updateKeysInFile() {
		List<String> temp = new ArrayList<String>();
		for(Map.Entry<Keyboard, String> entries : keys.entrySet()) {
			String key = String.valueOf(entries.getKey().getKeyCode()).concat(".").concat(entries.getValue());
			temp.add(key);
		}
		playerConfig.set("Keys", temp);
	}

	private void saveAmounts() {
		for (Map.Entry<String, Integer> entries : amounts.entrySet()) {
			playerConfig.set("Amount." + entries.getKey(), entries.getValue());
		}
	}
}