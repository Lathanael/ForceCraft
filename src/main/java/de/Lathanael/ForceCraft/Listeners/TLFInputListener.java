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

package de.Lathanael.ForceCraft.Listeners;

import org.bukkit.command.Command;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.BasePower;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class TLFInputListener extends InputListener {
	protected final ForcePlugin instance;

	public TLFInputListener(ForcePlugin instance) {
		this.instance = instance;
	}

	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(event.getPlayer().getName());
		SpoutPlayer sPlayer = event.getPlayer();
		if (fPlayer == null)
			return;
		if (!fPlayer.containsKey(event.getKey()))
			return;
		Command cmd = instance.getCommand(fPlayer.getKey(event.getKey()));
		sPlayer.getTargetBlock(null, 600);
		BasePower power = instance.commandsHandler.getCmdPower(cmd);
		if (power != null)
			instance.commandsHandler.executePower(sPlayer.getPlayer(), power, null);
	}
}
