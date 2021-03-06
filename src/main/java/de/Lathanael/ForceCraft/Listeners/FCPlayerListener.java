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

package de.Lathanael.ForceCraft.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCPlayerListener implements Listener {

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		ForcePlayer fp = PlayerHandler.getInstance().createForcePlayer(p.getName(), true);
		if (fp == null)
			return;
		String rankName = fp.getRankName();
		if (!rankName.equalsIgnoreCase("none")) {
			p.setDisplayName("[" + fp.getRankName() + "] " + p.getDisplayName());
		}
		Scheduler.getInstance().scheduleRefillManaTask(fp);
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) {
		if (event.isCancelled())
			return;
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		if (fPlayer != null) {
			fPlayer.updateFile(true);
			if ((event.getReason().toLowerCase().contains("flying") || event.getReason().toLowerCase()
					.contains("floating")) && fPlayer.hasPowerState(PlayerPowerStates.LIFTED))
				event.setCancelled(true);
			Scheduler.getInstance().scheduleCancelRefillManaTask(fPlayer);
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		if (fPlayer != null) {
			fPlayer.updateFile(true);
			Scheduler.getInstance().scheduleCancelRefillManaTask(fPlayer);
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		if (fPlayer != null && (fPlayer.hasPowerState(PlayerPowerStates.CHOKED)
				|| fPlayer.hasPowerState(PlayerPowerStates.SHOCKED)
				|| fPlayer.hasPowerState(PlayerPowerStates.LIFTED)))
			event.setCancelled(true);
		if (fPlayer.hasPowerState(PlayerPowerStates.FLASH))
			Tools.flash(fPlayer);
	}
}
