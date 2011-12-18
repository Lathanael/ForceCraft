/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of ForceCraft.
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

package de.Lathanael.ForceCraft.Players;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.keyboard.Keyboard;

import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Ranks;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;

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
	protected int maxMana = 100;
	protected File playerFile;
	protected long lastTimeCasted = 0;
	protected boolean mediation = false;
	protected HashSet<PlayerPowerStates> powers = new HashSet<PlayerPowerStates>();
	protected TreeMap<Keyboard, String> keys = new TreeMap<Keyboard, String>();
	protected HashMap<String, Integer> amounts = new HashMap<String, Integer>();
	protected HashMap<String, Long> times = new HashMap<String, Long>();
	protected HashMap<String, Integer> skillRanks = new HashMap<String, Integer>();

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
					ForcePlugin.log.info("[ForceCraft] Creating player file for: " + name);
				playerFile.createNewFile();
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				createDefaults();
				playerConfig.save(playerFile);
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				rank = Ranks.getRank(playerConfig.getString("Alignment").toLowerCase(), playerConfig.getInt("Rank"));
				alignment = ForceAlignment.valueOf(((String) playerConfig.get("Alignment")).toUpperCase());
				mana = playerConfig.getInt("Mana");
				maxMana = playerConfig.getInt("MaxMana");
				loadKeys();
				loadAmounts();
				loadSkillRanks();
				if (ForcePlugin.debug)
					ForcePlugin.log.info("[ForceCraft] Player file for " + name + " created!");
			} catch (IOException e) {
				ForcePlugin.log.info("[ForceCraft] An error occured during the creation of the player file for: " + name);
				e.printStackTrace();
			}
		} else {
			if (ForcePlugin.debug)
				ForcePlugin.log.info("[ForceCraft] Loading player file for: " + name);
			playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			rank = Ranks.getRank(playerConfig.getString("Alignment").toLowerCase(), playerConfig.getInt("Rank"));;
			alignment = ForceAlignment.valueOf(((String) playerConfig.get("Alignment")).toUpperCase());
			mana = playerConfig.getInt("Mana");
			maxMana = playerConfig.getInt("MaxMana");
			loadKeys();
			loadAmounts();
			loadSkillRanks();
			if (ForcePlugin.debug) {
				ForcePlugin.log.info("[ForceCraft] Loaded player file for: " + name);
				ForcePlugin.log.info("[ForceCraft] Loaded attributes are:");
				ForcePlugin.log.info("[ForceCraft] Alignment: " + alignment.toString());
				ForcePlugin.log.info("[ForceCraft] Rank: " + rank.toString());
				ForcePlugin.log.info("[ForceCraft] Keys:");
				for (Map.Entry<Keyboard, String> entries : keys.entrySet()) {
						ForcePlugin.log.info("[ForceCraft] Key: " + entries.getKey().toString());
						ForcePlugin.log.info("[ForceCraft] Power: " + entries.getValue());
				}
				ForcePlugin.log.info("[ForceCraft] Loaded player file for: " + name);
			}
		}
	}

	public void updateFile(boolean forceSave) {
		if (count == 5 || forceSave) {
			try {
				if (ForcePlugin.debug)
					ForcePlugin.log.info("[ForceCraft] Saving player file for: " + name);
				updateKeysInFile();
				saveAmounts();
				saveSkillRanks();
				playerConfig.save(playerFile);
			} catch (IOException e) {
				ForcePlugin.log.info("[ForceCraft] Failed to save player file for: " + name);
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

	public void incRank() {
		int rank = this.rank.getRankNr();
		if (rank <= 4)
			rank++;
		playerConfig.set("Rank", rank);
		this.rank = Ranks.getRank(alignment.toString().toLowerCase(), rank);
		updateFile();
	}

	public void decRank() {
		int rank = this.rank.getRankNr();
		if (rank >= 2)
			rank--;
		playerConfig.set("Rank", rank);
		this.rank = Ranks.getRank(alignment.toString().toLowerCase(), rank);
		updateFile();
	}

	public void setSkillRank(String power, int rank) {
		updateFile();
	}

	public int getSkillRank(String power) {
		if (skillRanks.containsKey(power))
			return skillRanks.get(power);
		return 0;
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
		while (entries.hasNext()) {
			if (entries.next().getKey() == key) {
				entries.remove();
				break;
			}
		}
		if (keys.size() < 3)
			keys.put(key, power);
		else {
			keys.remove(keys.firstKey());
			keys.put(key, power);
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


	public void increasePwrAmount(String powerName) {
		int amount = -1;
		if (amounts.containsKey(powerName))
			amount = amounts.get(powerName);
		if (amount != -1) {
			amounts.put(powerName, amount + 1);
			updateFile();
		}
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana){
		this.mana = mana;
		if (mana < maxMana)
			mana = maxMana;
		updateFile();
	}

	public void incMana(int mana){
		this.mana = this.mana + mana;
		if (mana < maxMana)
			mana = maxMana;
		updateFile();
	}

	public void decMana(int mana){
		this.mana = this.mana - mana;
		if (mana < 0)
			mana = 0;
		updateFile();
	}

	public Player getHandler() {
		return handler;
	}

	public void setPowerState(PlayerPowerStates state) {
		if (state != null)
			powers.add(state);
	}

	public void removePowerState(PlayerPowerStates state) {
		if (state != null && powers.contains(state))
			powers.remove(state);
	}

	public boolean hasPowerState(PlayerPowerStates state) {
		if (state != null)
			return powers.contains(state);
		else
			return false;
	}

	public void setLastTimeUsed(String powerName, long time) {
		times.put(powerName, time);
	}

	public long getLastTimeUsed(String powerName) {
		if (times.containsKey(powerName)) {
			long time = times.get(powerName);
			return time;
		}
		else
			return 0L;
	}

	public void resetLastTimeUsed() {
		times.clear();
	}

	public void setLastTimeCasted(long time) {
		lastTimeCasted = time;
	}

	public long getLastTimeCasted() {
		return lastTimeCasted;
	}

	public void setMaxMana(int mana) {
		maxMana = mana;
		updateFile();
	}

	public void incMaxMana(int mana) {
		maxMana = maxMana + mana;
		updateFile();
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
		playerConfig.addDefault("Skillrank.Pull", 0);
		playerConfig.addDefault("Skillrank.Push", 0);
		playerConfig.addDefault("Skillrank.Run", 0);
		playerConfig.addDefault("Skillrank.Jump", 0);
		playerConfig.addDefault("Skillrank.Heal", 0);
		playerConfig.addDefault("Skillrank.Lift", 0);
		playerConfig.addDefault("Skillrank.Choke", 0);
		playerConfig.addDefault("Skillrank.Rage", 0);
		playerConfig.addDefault("Skillrank.Flash", 0);
		playerConfig.addDefault("Skillrank.Mediation", 0);
		playerConfig.addDefault("Skillrank.Lightning", 0);
		playerConfig.addDefault("Skillrank.Shield", 0);
		playerConfig.addDefault("Mana", 100);
		playerConfig.addDefault("MaxMana", 100);
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

	private void loadSkillRanks() {
		Map<String, Object> tempList = playerConfig.getConfigurationSection("Skillrank").getValues(false);
		for (Map.Entry<String, Object> entries : tempList.entrySet()) {
			skillRanks.put(entries.getKey(), (Integer) entries.getValue());
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

	private void saveSkillRanks() {
		for (Map.Entry<String, Integer> entries : skillRanks.entrySet()) {
			playerConfig.set("Skillrank." + entries.getKey(), entries.getValue());
		}
	}
}