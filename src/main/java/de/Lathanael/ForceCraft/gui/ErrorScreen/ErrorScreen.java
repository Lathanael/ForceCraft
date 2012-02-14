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

package de.Lathanael.ForceCraft.gui.ErrorScreen;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.ErrorScreen.Buttons.ESCloseButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class ErrorScreen extends GenericPopup {
	private Texture background;
	private Label label;
	private Geometry edges;
	private ESCloseButton close;

	public ErrorScreen (SpoutPlayer player, String errorMsg) {
		int screenWidth = player.getMainScreen().getWidth();
		int screenHeight = player.getMainScreen().getHeight();
		background = new GenericTexture(ForcePlugin.backgroundTexURL);
		background.setHeight(50).setWidth(200).setX((screenWidth - 200)/2).setY((screenHeight-50)/2);
		edges.setLeft((screenWidth-190)/2);
		edges.setRight(background.getX()+background.getWidth()-10);
		edges.setTop(background.getY()+10);
		edges.setBottom(background.getHeight()+background.getY()-10);
		label = new GenericLabel(errorMsg);
		label.setTextColor(new Color(1.0F, 0, 0));
		label.setWidth(180).setHeight(30).setX(edges.getLeft()).setY(edges.getTop());
		close = new ESCloseButton("X");
		close.setHeight(10).setWidth(10).setX(edges.getRight()).setY(edges.getTop());
		close.setTooltip("Close the window");
		attachWidgets(ForcePlugin.getInstance(), background, label, close);
	}
}
