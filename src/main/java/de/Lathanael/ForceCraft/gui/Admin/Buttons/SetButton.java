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
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Ranks;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class SetButton extends GenericButton implements Button {

	public SetButton(String name) {
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
		String setType = FCUserInterface.adminField.setListBox.getSelectedItem();
		String setValue = FCUserInterface.adminField.setField.getText();
		int number = 0;
		if (setType.equalsIgnoreCase("mana")) {
			try {
				number = Tools.parseInteger(fPlayer, setValue, "mana");
			} catch (NumberFormatException e) {
				errorMsg();
				return;
			}
			fPlayer.setMana(number);
			successMsg();
		} else if (setType.equalsIgnoreCase("alignment")) {
			try {
				number = Tools.parseInteger(fPlayer, FCUserInterface.adminField.setAlignmentBox.getSelectedItem()
						, "alignment");
			} catch (NumberFormatException e) {
				errorMsg();
				return;
			}
			fPlayer.setAlignment(ForceAlignment.getAlignmentByNr(number));
			successMsg();
		} else if (setType.equalsIgnoreCase("rank")) {
			try {
				number = Tools.parseInteger(fPlayer, FCUserInterface.adminField.setRankBox.getSelectedItem()
						, "rank");
			} catch (NumberFormatException e) {
				errorMsg();
				return;
			}
			fPlayer.setRank(Ranks.getRank(FCUserInterface.adminField.setAlignmentBox.getSelectedItem(), number));
			successMsg();
		} else if (setType.equalsIgnoreCase("maxmana")) {
			try {
				number = Tools.parseInteger(fPlayer, setValue, "maxmana");
			} catch (NumberFormatException e) {
				errorMsg();
				return;
			}
			fPlayer.setMaxMana(number);
			successMsg();
		}
	}

	private void errorMsg() {
		FCUserInterface.adminField.output.setText("Sorry, could not parse the given String as a number.");
		FCUserInterface.adminField.output.setFieldColor(new Color(0.4F, 0, 0));
		FCUserInterface.adminField.output.setDirty(true);
	}

	private void successMsg() {
		FCUserInterface.adminField.output.setText("Successfully changed ForcePlayer object for: "
				+ FCUserInterface.adminField.name.getText());
		FCUserInterface.adminField.output.setFieldColor(new Color(0, 1.0F, 0.4F));
		FCUserInterface.adminField.output.setDirty(true);
	}
}
