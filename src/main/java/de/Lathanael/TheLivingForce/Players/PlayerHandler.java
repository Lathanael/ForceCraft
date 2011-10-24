package de.Lathanael.TheLivingForce.Players;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerHandler {

	private final Set<String> filePlayers = new HashSet<String>();
	private HashMap<String, ForcePlayer> players = new HashMap<String, ForcePlayer>();
	private File playerFolder;
	private FilenameFilter ymlFilter = new YamlFileFilter();

	public PlayerHandler(String dir) {
		playerFolder = new File(dir + File.separator + "players");
		initialize();
	}

	private void initialize() {
		File[] files = playerFolder.listFiles(ymlFilter);
		for (File file : files) {
			String name = file.getName();
			filePlayers.add(name.substring(0, name.lastIndexOf('.')));
		}
	}

	public ForcePlayer getPlayer(String name) {
		return players.get(name);
	}

	private ForcePlayer callForcePlayer(String player) {
		if (filePlayers.contains(player))
			return new ForcePlayer(player);
		else
			return new ForcePlayer(player, playerFolder.getPath());
	}

	public void addForcePlayer(String playerName) {
		players.put(playerName, callForcePlayer(playerName));
	}







/*-----------------------------FilenameFilter-------------------------------------*/

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
