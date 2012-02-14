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

package de.Lathanael.ForceCraft.gui.SkillTree;

import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.SkillTree.Buttons.MinusButton;
import de.Lathanael.ForceCraft.gui.SkillTree.Buttons.PlusButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class SkillTreeItem extends GenericContainer {

	private Texture skillImage;
	private Texture skillPoint1, skillPoint2, skillPoint3, skillPoint4, skillPoint5;
	private String skillNameString;
	private int skillRank;
	private ForcePlayer player;
	private PlusButton pb;
	private MinusButton mb;
	private String tex1URL, tex2URL;

	public SkillTreeItem(Geometry edges, Texture image, String skillNameString, ForcePlayer player) {
		this.skillNameString = skillNameString;
		this.player = player;
		this.skillRank = player.getSkillRank(skillNameString);
		tex1URL = ForcePlugin.skillPointTexURL;
		tex2URL = ForcePlugin.spHighlightedTexURL;
		skillImage = image;
		skillImage.setHeight(32).setWidth(32).setMaxHeight(32).setMaxWidth(32);
		skillImage.setMarginRight(30);
		skillImage.setTooltip("Force " + skillNameString);
		skillPoint1 = new GenericTexture(tex1URL);
		skillPoint1.setHeight(16).setWidth(16).setMaxHeight(16).setMaxWidth(16);
		skillPoint1.setMarginRight(1);
		skillPoint2 = new GenericTexture(tex1URL);
		skillPoint2.setHeight(16).setWidth(16).setMaxHeight(16).setMaxWidth(16);
		skillPoint2.setMarginLeft(1).setMarginRight(1);
		skillPoint3 = new GenericTexture(tex1URL);
		skillPoint3.setHeight(16).setWidth(16).setMaxHeight(16).setMaxWidth(16);
		skillPoint3.setMarginLeft(1).setMarginRight(1);
		skillPoint4 = new GenericTexture(tex1URL);
		skillPoint4.setHeight(16).setWidth(16).setMaxHeight(16).setMaxWidth(16);
		skillPoint4.setMarginLeft(1).setMarginRight(1);
		skillPoint5 = new GenericTexture(tex1URL);
		skillPoint5.setHeight(16).setWidth(16).setMaxHeight(16).setMaxWidth(16);
		skillPoint5.setMarginLeft(1).setMarginRight(1);
		pb = new PlusButton("+", skillNameString, player);
		pb.setWidth(16).setHeight(16).setMaxHeight(16).setMaxWidth(16);
		pb.setMarginLeft(9);
		mb = new MinusButton("-", skillNameString, player);
		mb.setWidth(16).setHeight(16).setMaxHeight(16).setMaxWidth(16);
		mb.setMarginLeft(2);
		setPointImage();
		setLayout(ContainerType.HORIZONTAL);
		addChildren(new Widget[] {skillImage, skillPoint1, skillPoint2, skillPoint3,
				skillPoint4, skillPoint5, pb, mb});

	}

	public void updateSkillPoints()	{
		skillRank = player.getSkillRank(skillNameString);
		setPointImage();
	}

	private void setPointImage() {
		switch (skillRank) {
			case 0:
				skillPoint1.setUrl(tex1URL);
				skillPoint1.setDirty(true);
				skillPoint2.setUrl(tex1URL);
				skillPoint2.setDirty(true);
				skillPoint3.setUrl(tex1URL);
				skillPoint3.setDirty(true);
				skillPoint4.setUrl(tex1URL);
				skillPoint4.setDirty(true);
				skillPoint5.setUrl(tex1URL);
				skillPoint5.setDirty(true);
				break;
			case 1:
				skillPoint1.setUrl(tex2URL);
				skillPoint1.setDirty(true);
				skillPoint2.setUrl(tex1URL);
				skillPoint2.setDirty(true);
				skillPoint3.setUrl(tex1URL);
				skillPoint3.setDirty(true);
				skillPoint4.setUrl(tex1URL);
				skillPoint4.setDirty(true);
				skillPoint5.setUrl(tex1URL);
				skillPoint5.setDirty(true);
				break;
			case 2:
				skillPoint1.setUrl(tex2URL);
				skillPoint1.setDirty(true);
				skillPoint2.setUrl(tex2URL);
				skillPoint2.setDirty(true);
				skillPoint3.setUrl(tex1URL);
				skillPoint3.setDirty(true);
				skillPoint4.setUrl(tex1URL);
				skillPoint4.setDirty(true);
				skillPoint5.setUrl(tex1URL);
				skillPoint5.setDirty(true);
				break;
			case 3:
				skillPoint1.setUrl(tex2URL);
				skillPoint1.setDirty(true);
				skillPoint2.setUrl(tex2URL);
				skillPoint2.setDirty(true);
				skillPoint3.setUrl(tex2URL);
				skillPoint3.setDirty(true);
				skillPoint4.setUrl(tex1URL);
				skillPoint4.setDirty(true);
				skillPoint5.setUrl(tex1URL);
				skillPoint5.setDirty(true);
				break;
			case 4:
				skillPoint1.setUrl(tex2URL);
				skillPoint1.setDirty(true);
				skillPoint2.setUrl(tex2URL);
				skillPoint2.setDirty(true);
				skillPoint3.setUrl(tex2URL);
				skillPoint3.setDirty(true);
				skillPoint4.setUrl(tex2URL);
				skillPoint4.setDirty(true);
				skillPoint5.setUrl(tex1URL);
				skillPoint5.setDirty(true);
				break;
			case 5:
				skillPoint1.setUrl(tex2URL);
				skillPoint1.setDirty(true);
				skillPoint2.setUrl(tex2URL);
				skillPoint2.setDirty(true);
				skillPoint3.setUrl(tex2URL);
				skillPoint3.setDirty(true);
				skillPoint4.setUrl(tex2URL);
				skillPoint4.setDirty(true);
				skillPoint5.setUrl(tex2URL);
				skillPoint5.setDirty(true);
				break;
		}
	}
}
