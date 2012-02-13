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

import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class CreateButton extends GenericButton implements Button {

	public CreateButton(String name) {
		super(name);
	}

	public void onButtonClick(ButtonClickEvent event) {
		PlayerHandler.getInstance().createNewForcePlayer(FCUserInterface.adminField.name.getText());
		FCUserInterface.adminField.output.setText("Create ForcePlayer object for: "
				+ FCUserInterface.adminField.name.getText());
		FCUserInterface.adminField.output.setFieldColor(new Color(0, 1.0F, 0.4F));
		FCUserInterface.adminField.output.setDirty(true);
	}
}
