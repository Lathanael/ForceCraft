/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
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

package de.Lathanael.ForceCraft.gui.Admin.Buttons;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PromoteButton extends GenericButton implements Button {

	public PromoteButton(String name) {
		super(name);
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		ForcePlayer fPlayer = PlayerHandler.getInstance()
				.getPlayer(FCUserInterface.adminField.name.getText());
		if (fPlayer == null) {
			FCUserInterface.adminField.output.setText("Sorry no ForcePlayer with the name: "
					+ FCUserInterface.adminField.name.getText() + " was found.");
			FCUserInterface.adminField.output.setFieldColor(new Color(0.4F, 0, 0));
			FCUserInterface.adminField.output.setDirty(true);
			return;
		}
		fPlayer.incRank();
		FCUserInterface.adminField.output.setText("Successfully promoted Player: "
				+ FCUserInterface.adminField.name.getText());
		FCUserInterface.adminField.output.setFieldColor(new Color(0, 1.0F, 0.4F));
		FCUserInterface.adminField.output.setDirty(true);
	}
}
