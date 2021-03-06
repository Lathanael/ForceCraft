/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Ranks;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.gui.ManaBarGUI;
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
	protected int maxRank = 5;
	protected File playerFile;
	protected long lastTimeCasted = 0;
	protected HashSet<PlayerPowerStates> powers = new HashSet<PlayerPowerStates>();
	protected HashSet<PlayerPowerStates> lastStates = new HashSet<PlayerPowerStates>();
	protected TreeMap<Keyboard, String> keys = new TreeMap<Keyboard, String>();
	protected HashMap<String, Integer> amounts = new HashMap<String, Integer>();
	protected HashMap<String, Long> times = new HashMap<String, Long>();
	protected HashMap<String, Integer> skillRanks = new HashMap<String, Integer>();
	protected ManaBarGUI manaBar;
	protected SpoutPlayer sPlayer;
	protected int usedSP;
	protected int availableSP;

	public ForcePlayer (String playerName, String dir) {
		this.name = playerName;
		this.handler = ForcePlugin.getInstance().getServer().getPlayer(playerName);
		sPlayer = (SpoutPlayer) handler;
		initilize(dir);
	}

	public ForcePlayer (Player player, String dir) {
		this.name = player.getName();
		this.handler = player;
		sPlayer = (SpoutPlayer) player;
		initilize(dir);
	}

	public ForcePlayer (String name) {
		this.name = name;
		this.handler = ForcePlugin.getInstance().getServer().getPlayer(name);
		sPlayer = (SpoutPlayer) handler;
		initilize(ForcePlugin.getInstance().getDataFolder()	+ File.separator + "players");
	}

	private void initilize(String dir) {
		this.playerFile = new File(dir, name + ".yml");
		if (!playerFile.exists()) {
			try {
				if (ForcePlugin.debug)
					ForcePlugin.log.info("Creating player file for: " + name);
				playerFile.createNewFile();
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				createDefaults();
				playerConfig.save(playerFile);
				playerConfig = YamlConfiguration.loadConfiguration(playerFile);
				alignment = ForceAlignment.valueOf(((String) playerConfig.get("Alignment")).toUpperCase());
				rank = Ranks.getRank(playerConfig.getString("Alignment").toLowerCase(), playerConfig.getInt("Rank"));
				mana = playerConfig.getInt("Mana");
				maxMana = playerConfig.getInt("MaxMana");
				availableSP = playerConfig.getInt("availableSkillPoints");
				usedSP = playerConfig.getInt("usedSkillPoints");
				loadKeys();
				loadAmounts();
				loadSkillRanks();
				if (ForcePlugin.debug)
					ForcePlugin.log.info("Player file for " + name + " created!");
			} catch (IOException e) {
				ForcePlugin.log.info("An error occured during the creation of the player file for: " + name);
				e.printStackTrace();
			}
		} else {
			if (ForcePlugin.debug)
				ForcePlugin.log.info("Loading player file for: " + name);
			playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			rank = Ranks.getRank(playerConfig.getString("Alignment").toLowerCase(), playerConfig.getInt("Rank"));;
			alignment = ForceAlignment.valueOf(((String) playerConfig.get("Alignment")).toUpperCase());
			mana = playerConfig.getInt("Mana");
			maxMana = playerConfig.getInt("MaxMana");
			availableSP = playerConfig.getInt("availableSkillPoints");
			usedSP = playerConfig.getInt("usedSkillPoints");
			loadKeys();
			loadAmounts();
			loadSkillRanks();
			if (ForcePlugin.debug) {
				ForcePlugin.log.info("Loaded player file for: " + name);
				ForcePlugin.log.info("Loaded attributes are:");
				ForcePlugin.log.info("Alignment: " + alignment.toString());
				ForcePlugin.log.info("Rank: " + rank.toString());
				ForcePlugin.log.info("Mana: " + mana + "/" + maxMana);
				ForcePlugin.log.info("Used/Available Skillpoints: " + usedSP + "/" + availableSP);
				ForcePlugin.log.info("Keys:");
				for (Map.Entry<Keyboard, String> entries : keys.entrySet()) {
						ForcePlugin.log.info("Key: " + entries.getKey().toString());
						ForcePlugin.log.info("Power: " + entries.getValue());
				}
				ForcePlugin.log.info("Loaded player file for: " + name);
			}
		}
		if (ForcePlugin.manaBarEnabled) {
			manaBar = new ManaBarGUI((SpoutPlayer) handler, mana, maxMana);
			sPlayer.getMainScreen().attachWidget(ForcePlugin.getInstance(), manaBar);
		}
	}

	public void updateFile(boolean forceSave) {
		if (count == 5 || forceSave) {
			try {
				if (ForcePlugin.debug)
					ForcePlugin.log.info("Saving player file for: " + name);
				updateKeysInFile();
				saveAmounts();
				saveSkillRanks();
				saveLoneValues();
				playerConfig.save(playerFile);
			} catch (IOException e) {
				ForcePlugin.log.info("Failed to save player file for: " + name);
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

	public boolean setSkillRank(String power, int skillLevel) {
		if (skillRanks.containsKey(power)) {
			int oldRank = skillRanks.get(power);
			int newRank = oldRank + skillLevel;
			if (newRank > 5)
				newRank = 5;
			else if (newRank < 0)
				newRank = 0;
			int diff = Math.abs((newRank - oldRank));
			if (newRank > oldRank) {
				if (!checkSkillPointUse(false))
					return false;
				incUsedSkillPoints(diff);
			} else if (oldRank > newRank) {
				if (!checkSkillPointUse(true))
					return false;
				decUsedSkillPoints(diff);
			}
			skillRanks.put(power, newRank);
			updateFile();
			return true;
		}
		return false;
	}

	public int getSkillRank(String power) {
		if (skillRanks.containsKey(power))
			return skillRanks.get(power);
		return 0;
	}

	public boolean incSkillRank(String power) {
		if (!checkSkillPointUse(false))
			return false;
		if (skillRanks.containsKey(power)) {
			int rank = skillRanks.get(power);
			rank = rank + 1;
			if (rank > maxRank) {
				rank = maxRank;
				skillRanks.put(power, rank);
				return false;
			}
			skillRanks.put(power, rank);
			incUsedSkillPoints(1);
			return true;
		}
		return false;
	}

	public boolean decSkillRank(String power) {
		if (!checkSkillPointUse(true))
			return false;
		if (skillRanks.containsKey(power)) {
			int rank = skillRanks.get(power);
			rank = rank - 1;
			if (rank < 0) {
				rank = 0;
				skillRanks.put(power, rank);
				return false;
			}
			skillRanks.put(power, rank);
			decUsedSkillPoints(1);
			return true;
		}
		return false;
	}

	public void incAvailableSkillPoints(int amount) {
		availableSP += amount;
		if (availableSP > ForcePlugin.maxSP)
			availableSP = ForcePlugin.maxSP;
	}

	public void setAvailableSkillPoints(int amount) {
		availableSP = amount;
		if (availableSP > ForcePlugin.maxSP)
			availableSP = ForcePlugin.maxSP;
	}

	public int getAvailableSkillPoints() {
		return availableSP;
	}

	public void incUsedSkillPoints(int amount) {
		usedSP += amount;
	}

	public void decUsedSkillPoints(int amount) {
		usedSP -= amount;
	}

	public void setUsedSkillPoints(int amount) {
		usedSP = amount;
	}

	public int getUsedSkillPoints() {
		return usedSP;
	}

	public boolean checkSkillPointUse(boolean decrease) {
		int incUsedSP = usedSP + 1;
		int decUsedSP = usedSP - 1;
		if (incUsedSP > availableSP && !decrease)
			return false;
		else if (decrease && decUsedSP < 0)
			return false;
		return true;
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

	public void resetKeys() {
		keys.clear();
	}

	public void removeKey(Keyboard key) {
		Iterator<Map.Entry<Keyboard, String>> entries = keys.entrySet().iterator();
		while (entries.hasNext()) {
			if (entries.next().getKey() == key) {
				entries.remove();
				break;
			}
		}
	}

	public TreeMap<Keyboard, String> getKeys() {
		return keys;
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
		if (this.mana > maxMana)
			this.mana = maxMana;
		manaBar.updateMana(this.mana);
		updateFile();
	}

	public void incMana(int mana){
		this.mana = this.mana + mana;
		if (this.mana > maxMana)
			this.mana = maxMana;
		manaBar.updateMana(this.mana);
		updateFile();
	}

	public void decMana(int mana){
		this.mana = this.mana - mana;
		if (this.mana < 0)
			this.mana = 0;
		manaBar.updateMana(this.mana);
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

	public void addLastState(PlayerPowerStates state) {
		lastStates.add(state);
	}

	public void removeLastState(PlayerPowerStates state) {
		if (lastStates.contains(state))
			lastStates.remove(state);
	}

	public boolean containsLastState(PlayerPowerStates state) {
		return lastStates.contains(state);
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
		manaBar.updateMaxMana(this.maxMana);
		updateFile();
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void incMaxMana(int mana) {
		maxMana = maxMana + mana;
		manaBar.updateMaxMana(this.maxMana);
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
		playerConfig.addDefault("Amount.Meditation", 0);
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
		playerConfig.addDefault("Skillrank.Meditation", 0);
		playerConfig.addDefault("Skillrank.Lightning", 0);
		playerConfig.addDefault("Skillrank.Shield", 0);
		playerConfig.addDefault("Mana", 100);
		playerConfig.addDefault("MaxMana", 100);
		playerConfig.addDefault("availableSkillPoints", ForcePlugin.startingSP);
		playerConfig.addDefault("usedSkillPoints", 0);
		playerConfig.options().copyDefaults(true);
	}

	private void loadKeys() {
		List<String> tempKeys = playerConfig.getStringList("Keys");
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

	private void saveLoneValues() {
		playerConfig.set("Mana", mana);
		playerConfig.set("MaxMana", maxMana);
		playerConfig.set("Rank", rank.getRankNr());
		playerConfig.set("Alignment", alignment.toString());
		playerConfig.set("availableSkillPoints", availableSP);
		playerConfig.set("usedSkillPoints", usedSP);
	}
}