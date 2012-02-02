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

import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.BasePower;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCInputListener implements Listener {
	protected final ForcePlugin instance;

	public FCInputListener(ForcePlugin instance) {
		this.instance = instance;
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onKeyPressedEvent(KeyPressedEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		SpoutPlayer sPlayer = event.getPlayer();
		if (fPlayer == null || sPlayer == null)
			return;

		//Player is in Chat, Chest or any other Screen than the main screen!
		if (!sPlayer.getActiveScreen().equals(ScreenType.GAME_SCREEN))
			return;

		// Player pressed Jump
		if (event.getKey().equals(sPlayer.getJumpKey())) {
			if (!fPlayer.hasPowerState(PlayerPowerStates.JUMP))
				return;
			Command cmd = instance.getCommand("fc_jump");
			BasePower power = instance.commandsHandler.getPower(cmd);
			if (power == null)
				return;
			double mult = instance.ranksInfo.getDouble("Run." + String.valueOf(fPlayer.getRank()), 1);
			sPlayer.setJumpingMultiplier(mult);
		}

		// Player pressed any of the Movement-Keys
		if (event.getKey().equals(sPlayer.getForwardKey()) || event.getKey().equals(sPlayer.getBackwardKey()) || event.getKey().equals(sPlayer.getLeftKey())
				|| event.getKey().equals(sPlayer.getRightKey())) {
			if (!fPlayer.hasPowerState(PlayerPowerStates.RUN))
				return;
			Command cmd = instance.getCommand("fc_run");
			BasePower power = instance.commandsHandler.getPower(cmd);
			if (power == null)
				return;
			double mult = instance.ranksInfo.getDouble("Run." + String.valueOf(fPlayer.getRank()), 1);
			sPlayer.setWalkingMultiplier(mult);
		}

		// Did the player press any button bound to a power? If yes, execute it else return.
		if (!fPlayer.containsKey(event.getKey()))
			return;
		Command cmd = instance.getCommand("fc_" + fPlayer.getKey(event.getKey()));
		Entity target = Tools.getTargetedEntity(sPlayer.getPlayer(), false, ForcePlugin.checkDist);
		BasePower power = instance.commandsHandler.getPower(cmd);
		if (power != null)
			instance.commandsHandler.executePower(sPlayer.getPlayer(), power, target);
	}
}
