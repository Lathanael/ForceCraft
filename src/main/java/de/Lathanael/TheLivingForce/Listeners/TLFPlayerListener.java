package de.Lathanael.TheLivingForce.Listeners;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Lathanael.TheLivingForce.Players.ForcePlayer;
import de.Lathanael.TheLivingForce.Players.PlayerHandler;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class TLFPlayerListener extends PlayerListener {

	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerHandler.getInstance().createForcePlayer(event.getPlayer().getName());
	}

	public void onPlayerKick(PlayerKickEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		fPlayer.updateFile(true);
	}

	public void onPlayerQuit(PlayerQuitEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		fPlayer.updateFile(true);
	}
}
