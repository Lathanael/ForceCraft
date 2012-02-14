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

package de.Lathanael.ForceCraft.gui.SkillTree.Buttons;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class NextButton extends GenericButton implements Button {

	private ForcePlayer player;

	public NextButton(String text, ForcePlayer player) {
		super(text);
		this.player = player;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if (FCUserInterface.skillTreeField1.isVisible()) {
			FCUserInterface.closeSkillTreePage1();
			FCUserInterface.openSkillTreePage2();
		} else if (FCUserInterface.skillTreeField2.isVisible() && player.getAlignment().equals(ForceAlignment.LIGHT)) {
			FCUserInterface.closeSkillTreePage2();
			FCUserInterface.openSkillTreePageLight();
		} else if (FCUserInterface.skillTreeField2.isVisible() && player.getAlignment().equals(ForceAlignment.DARK)) {
			FCUserInterface.closeSkillTreePage2();
			FCUserInterface.openSkillTreePageDark();
		}
	}
}
