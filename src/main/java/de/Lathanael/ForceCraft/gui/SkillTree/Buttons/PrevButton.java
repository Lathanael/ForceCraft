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

import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PrevButton extends GenericButton implements Button {

	public PrevButton(String text) {
		super(text);
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if (FCUserInterface.skillTreeField2.isVisible()) {
			FCUserInterface.closeSkillTreePage2();
			FCUserInterface.openSkillTreePage1();
		} else if (FCUserInterface.skillTreeFieldLight.isVisible()) {
			FCUserInterface.closeSkillTreePageLight();
			FCUserInterface.openSkillTreePage2();
		}  else if (FCUserInterface.skillTreeFieldDark.isVisible()) {
			FCUserInterface.closeSkillTreePageDark();
			FCUserInterface.openSkillTreePage2();
		}
	}
}
