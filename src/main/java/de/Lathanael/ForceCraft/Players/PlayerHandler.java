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
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class PlayerHandler {

	private final static Set<String> filePlayers = new HashSet<String>();
	private HashMap<String, ForcePlayer> players = new HashMap<String, ForcePlayer>();
	private File playerFolder;
	private FilenameFilter ymlFilter = new YamlFileFilter();
	private static PlayerHandler handler = null;

	public PlayerHandler() {
	}

	/**
	 * Starts the Playerhandler, loads all players already having a file into a list.
	 *
	 * @param dir - The directory the player files are located in
	 */
	public void initialize(String dir) {
		playerFolder = new File(dir + File.separator + "players");
		if (!playerFolder.exists()) {
			playerFolder.mkdirs();
		} else {
			File[] files = playerFolder.listFiles(ymlFilter);
			for (File file : files) {
				String name = file.getName();
				filePlayers.add(name.substring(0, name.lastIndexOf('.')));
			}
		}
	}

	/**
	 * Gets a ForcePlayer by the Name of a player.
	 *
	 * @param name - The String representing a Players name (use getName() NOT getDisplayName()!!)
	 * @return The ForcePlayer object or null if it does not exist
	 */
	public ForcePlayer getPlayer(String name) {
		if (players.containsKey(name))
			return players.get(name);
		else if (filePlayers.contains(name))
			return new ForcePlayer(name);
		else
			return null;
	}

	/**
	 * Creates a new ForcePlayer object or loads one from file.
	 *
	 * @param playerName - The String representing a Players name
	 *                     (use getName() NOT getDisplayName()!!)
	 * @param join - Did the player join the server or not, ie was the code called
	 *               from the Player listener or not.
	 * @return The ForcePlayer object, either a new on with default values or loaded from file.
	 */
	private ForcePlayer callForcePlayer(String playerName, boolean join) {
		if (filePlayers.contains(playerName))
			return new ForcePlayer(playerName);
		if (ForcePlugin.sensitiveonJoin && join) {
			if (ForcePlugin.debug)
				ForcePlugin.log.info("Creating of a ForcePlayer object if a new player joins is disabled!");
			return null;
		}
		filePlayers.add(playerName);
		return new ForcePlayer(playerName, playerFolder.getPath());
	}

	/**
	 * Trys to create a ForcePlayer object for a given player.
	 *
	 * @param playerName - The String representing a Players name
	 *                     (use getName() NOT getDisplayName()!!)
	 */
	public void createForcePlayer(String playerName) {
		ForcePlayer fPlayer = callForcePlayer(playerName, false);
		if (fPlayer != null)
			players.put(playerName, fPlayer);
		if (ForcePlugin.debug && fPlayer == null)
			ForcePlugin.log.info("FocrePlayer object is null, creation failed.");
	}

	/**
	 * Trys to create a ForcePlayer object for a given player.
	 *
	 * @param playerName - The String representing a Players name
	 *                     (use getName() NOT getDisplayName()!!)
	 * @param join - Did the player join the server or not, ie was the code called
	 *               from the Player listener or not.
	 */
	public void createForcePlayer(String playerName, boolean join) {
		ForcePlayer fPlayer = callForcePlayer(playerName, join);
		if (fPlayer != null)
			players.put(playerName, fPlayer);
		if (ForcePlugin.debug && fPlayer == null)
			ForcePlugin.log.info("FocrePlayer object is null, creation failed.");
	}

	/**
	 * Creates a new ForcePlayer object.
	 *
	 * @param playerName - The String representing a Players name
	 *                     (use getName() NOT getDisplayName()!!)
	 */
	public void createNewForcePlayer(String playerName) {
		if (filePlayers.contains(playerName)) {
			players.put(playerName, new ForcePlayer(playerName));
		} else {
			filePlayers.add(playerName);
			players.put(playerName, new ForcePlayer(playerName, playerFolder.getPath()));
		}
	}

	public static void setInstance() {
		if (handler != null)
			return;
		handler = new PlayerHandler();
	}

	public static PlayerHandler getInstance() {
		return handler;
	}

	public HashMap<String, ForcePlayer> getPlayerList() {
		return players;
	}
/*-----------------------------FilenameFilter-------------------------------------*/

	/*
	 * Gets all files with the extension '.yml'
	 */
	private class YamlFileFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			if (name.endsWith(".yml")) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
