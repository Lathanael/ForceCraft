/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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

package de.Lathanael.ForceCraft.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.Lathanael.ForceCraft.Players.ForcePlayer;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ManaChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private ForcePlayer player;
	private int mana;

	public ManaChangeEvent (ForcePlayer player, int mana) {
		this.player = player;
		this.mana = mana;
	}

	/**
	 * Gets the ForcePlayer associated with the event.
	 *
	 * @return
	 */
	public ForcePlayer getPlayer() {
		return player;
	}

	/**
	 * Gets the Amount of mana wich was consumed in the event.
	 *
	 * @return
	 */
	public int getMana() {
		return mana;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
