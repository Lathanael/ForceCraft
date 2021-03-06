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

package de.Lathanael.ForceCraft.Events;

import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCKeyBinding implements BindingExecutionDelegate{

	@Override
	public void keyPressed(KeyBindingEvent event) {
		SpoutPlayer player = event.getPlayer();
		if (player.getActiveScreen() != ScreenType.GAME_SCREEN)
			return;
		if (event.getBinding().getId() == "ForceCraft GUI") {
			FCUserInterface popup = new FCUserInterface(player);
			popup.open();
		}
	}

	@Override
	public void keyReleased(KeyBindingEvent arg0) {
	}

}
