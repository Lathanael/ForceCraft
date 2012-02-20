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
import de.Lathanael.ForceCraft.gui.FCUserInterface;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PlusButton extends GenericButton implements Button {

	private String power;
	private ForcePlayer player;

	public PlusButton(String label, String power, ForcePlayer player) {
		super(label);
		this.power = power;
		this.player = player;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if (player.checkSkillPointUse(false)) {
			if (player.incSkillRank(power)) {
				if (FCUserInterface.skillTreeField1.treeItem.containsKey(power)) {
					FCUserInterface.skillTreeField1.treeItem.get(power).updateSkillPoints();
					FCUserInterface.skillTreeField1.updateskillPoints();
				} else if (FCUserInterface.skillTreeField2.treeItem.containsKey(power)) {
					FCUserInterface.skillTreeField2.treeItem.get(power).updateSkillPoints();
					FCUserInterface.skillTreeField2.updateskillPoints();
				}  else if (FCUserInterface.skillTreeFieldLight.treeItem.containsKey(power)) {
					FCUserInterface.skillTreeFieldLight.treeItem.get(power).updateSkillPoints();
					FCUserInterface.skillTreeFieldLight.updateskillPoints();
				} else if (FCUserInterface.skillTreeFieldDark.treeItem.containsKey(power)) {
					FCUserInterface.skillTreeFieldDark.treeItem.get(power).updateSkillPoints();
					FCUserInterface.skillTreeFieldDark.updateskillPoints();
				}
			}
		}
	}
}
