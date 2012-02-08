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

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Screen;

import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCButtonListener implements Listener {
	protected final ForcePlugin instance;

	public FCButtonListener(ForcePlugin instance) {
		this.instance = instance;
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onButtonClick(ButtonClickEvent event) {
		Screen screen = event.getScreen();
		if(screen instanceof FCUserInterface) {
			FCUserInterface popup = (FCUserInterface) screen;
			popup.onButtonClick(event.getButton());
		}
	}
}
