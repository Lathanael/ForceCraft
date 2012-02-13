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

package de.Lathanael.ForceCraft.gui.SkillTree;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.SkillTree.Buttons.NextButton;
import de.Lathanael.ForceCraft.gui.SkillTree.Buttons.PrevButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class SKGUIPage3 extends GenericContainer {
	private Label label, warning, skillPoints;
	@SuppressWarnings("unused")
	private SpoutPlayer player;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;
	private PrevButton prev;
	private NextButton next;

	public SKGUIPage3 (Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		background = tex;
		label = new GenericLabel();
		label.setText("Skill Tree Page 3");
		label.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop());
		label.setDirty(true);
		next = new NextButton("Next Page");
		next.setHeight(15).setWidth(60).setX(edges.getLeft() + 250).setY(edges.getTop() + 160);
		next.setEnabled(false);
		next.setDirty(true);
		prev = new PrevButton("Prev. Page");
		prev.setHeight(15).setWidth(60).setX(edges.getLeft() + 10).setY(edges.getTop() + 160);
		prev.setDirty(true);
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(player.getName());
		warning = new GenericLabel();
		warning.setHeight(15).setWidth(100).setX(edges.getLeft() + 10).setY(edges.getTop() + 100);
		warning.setText("");
		warning.setVisible(false);
		warning.setDirty(true);
		skillPoints = new GenericLabel();
		skillPoints.setHeight(15).setWidth(150).setX(edges.getLeft() + 80).setY(edges.getTop() + 160);
		skillPoints.setVisible(false);
		skillPoints.setDirty(true);
		if (fPlayer == null) {
			prev.setEnabled(false);
			prev.setDirty(true);
			warning.setText("Sorry, a ForcePlayer with your name was not found!");
			warning.setTextColor(new Color(1.0F, 0, 0));
			warning.setVisible(true);
			warning.setDirty(true);
			return;
		}
		skillPoints.setText("Skillpoints: " + fPlayer.getUsedSkillPoints() + "/" + fPlayer.getAvailableSkillPoints());
		skillPoints.setVisible(true);
		skillPoints.setDirty(true);
		addChildren(new Widget[] {label, prev, next, warning, skillPoints});
		setWidth(0).setHeight(0);
	}
}
