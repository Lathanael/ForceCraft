package de.Lathanael.TheLivingForce.Players;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class PlayerHandler {

	private final static Set<String> filePlayers = new HashSet<String>();
	private HashMap<String, ForcePlayer> players = new HashMap<String, ForcePlayer>();
	private File playerFolder;
	private FilenameFilter ymlFilter = new YamlFileFilter();
	private static PlayerHandler handler= null;

	public PlayerHandler() {
	}

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

	public ForcePlayer getPlayer(String name) {
		return players.get(name);
	}

	private ForcePlayer callForcePlayer(String playerName) {
		if (!filePlayers.contains(playerName)) {
			if (ForcePlugin.getInstance().config.getBoolean("DebugMessages"))
				ForcePlugin.log.info("Creating new player!");
			return new ForcePlayer(playerName);
		} else {
			if (ForcePlugin.getInstance().config.getBoolean("DebugMessages"))
				ForcePlugin.log.info("Loading playerfile!");
			return new ForcePlayer(playerName, playerFolder.getPath());
		}
	}

	public void createForcePlayer(String playerName) {
		players.put(playerName, callForcePlayer(playerName));
	}



	public static void setInstance() {
		if (handler != null)
			return;
		handler = new PlayerHandler();
	}

	public static PlayerHandler getInstance() {
		return handler;
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
