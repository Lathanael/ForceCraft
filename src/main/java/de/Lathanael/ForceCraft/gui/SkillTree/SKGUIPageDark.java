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

import java.util.HashMap;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
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
public class SKGUIPageDark extends GenericContainer {
	private Label label, warning, skillPoints, alignment;
	private ForcePlayer player;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;
	private PrevButton prev;
	private NextButton next;
	private SkillTreeItem ski1, ski2, ski3;
	public HashMap<String, SkillTreeItem> treeItem = new HashMap<String, SkillTreeItem>();

	public SKGUIPageDark (Geometry edges, SpoutPlayer player, Texture tex) {
		this.edges = edges;
		background = tex;
		label = new GenericLabel();
		label.setText("Skill Tree");
		label.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop());
		label.setDirty(true);
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(player.getName());
		if (fPlayer == null) {
			prev.setEnabled(false);
			prev.setDirty(true);
			warning.setText("Sorry, a ForcePlayer with your name was not found!");
			warning.setTextColor(new Color(1.0F, 0, 0));
			warning.setVisible(true);
			warning.setDirty(true);
			return;
		}
		this.player = fPlayer;
		alignment = new GenericLabel("Dark Powers");
		alignment.setHeight(10).setWidth(100).setX(edges.getLeft() + 10).setY(edges.getTop() + 20);
		next = new NextButton("Next Page", fPlayer);
		next.setHeight(15).setWidth(60).setX(edges.getLeft() + 250).setY(edges.getTop() + 169);
		next.setEnabled(false);
		next.setDirty(true);
		prev = new PrevButton("Prev. Page");
		prev.setHeight(15).setWidth(60).setX(edges.getLeft() + 10).setY(edges.getTop() + 169);
		prev.setDirty(true);
		warning = new GenericLabel();
		warning.setHeight(15).setWidth(100).setX(edges.getLeft() + 10).setY(edges.getTop() + 100);
		warning.setText("");
		warning.setVisible(false);
		warning.setDirty(true);
		skillPoints = new GenericLabel();
		skillPoints.setHeight(15).setWidth(150).setX(edges.getLeft() + 120).setY(edges.getTop() + 172);
		skillPoints.setVisible(false);
		skillPoints.setDirty(true);
		skillPoints.setText("Skillpoints: " + fPlayer.getUsedSkillPoints() + "/" + fPlayer.getAvailableSkillPoints());
		skillPoints.setVisible(true);
		skillPoints.setDirty(true);
		ski1 = new SkillTreeItem(edges,
				new GenericTexture("http://dl.dropbox.com/u/42731731/Power_Back.png"), "Choke", fPlayer);
		ski1.setHeight(36).setWidth(300).setX(edges.getLeft() + 10).setY(edges.getTop() + 45);
		treeItem.put("Choke", ski1);
		ski2 = new SkillTreeItem(edges,
				new GenericTexture("http://dl.dropbox.com/u/42731731/Power_Back.png"), "Rage", fPlayer);
		ski2.setHeight(36).setWidth(300).setX(edges.getLeft() + 10).setY(edges.getTop() + 85);
		treeItem.put("Rage", ski2);
		ski3 = new SkillTreeItem(edges,
				new GenericTexture("http://dl.dropbox.com/u/42731731/Power_Back.png"), "Lightning", fPlayer);
		ski3.setHeight(36).setWidth(300).setX(edges.getLeft() + 10).setY(edges.getTop() + 125);
		treeItem.put("Lightning", ski3);
		addChildren(new Widget[] {alignment, label, prev, next, warning, skillPoints, ski1, ski2, ski3});
		setWidth(0).setHeight(0);
	}

	public void updateskillPoints() {
		skillPoints.setText("Skillpoints: " + player.getUsedSkillPoints() + "/" + player.getAvailableSkillPoints());
		skillPoints.setDirty(true);
	}
}
