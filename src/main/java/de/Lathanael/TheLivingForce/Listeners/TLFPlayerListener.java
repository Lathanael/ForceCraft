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
