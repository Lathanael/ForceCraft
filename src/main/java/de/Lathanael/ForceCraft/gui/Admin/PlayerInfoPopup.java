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

package de.Lathanael.ForceCraft.gui.Admin;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericListView;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.bukkit.TextureURLs;
import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.PICloseButton;
import de.Lathanael.ForceCraft.gui.PlayerInfo.PIListModel;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PlayerInfoPopup extends GenericPopup {
	private GenericListView list;
	private Texture background;
	private PICloseButton close;

	public PlayerInfoPopup(SpoutPlayer player, Geometry edges) {
		int screenWidth = player.getMainScreen().getWidth();
		int screenHeight = player.getMainScreen().getHeight();
		background = new GenericTexture(TextureURLs.getBackground("BackgroundTexURL"));
		background.setHeight(200).setWidth(400).setX((screenWidth - 400)/2).setY((screenHeight-200)/2);
		background.setPriority(RenderPriority.Highest);
		attachWidget(ForcePlugin.getInstance(), background);
		list = new GenericListView(new PIListModel(player));
		list.setHeight(160).setWidth(360).setX(edges.getLeft()+10).setY(edges.getTop()+20);
		attachWidget(ForcePlugin.getInstance(), list);
		close = new PICloseButton(ChatColor.WHITE + "X");
		close.setAlign(WidgetAnchor.CENTER_CENTER);
		close.setHeight(10).setWidth(10).setX(edges.getRight()-10).setY(edges.getTop());
		close.setTooltip("Close the Window");
		attachWidget(ForcePlugin.getInstance(), close);

		player.getMainScreen().attachPopupScreen(this);
	}
}
