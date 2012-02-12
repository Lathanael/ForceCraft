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

package de.Lathanael.ForceCraft.gui.PlayerInfo;

import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericListView;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.gui.Geometry;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PIGUI extends GenericContainer{
	private Label guiLabel;
	@SuppressWarnings("unused")
	private SpoutPlayer player;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;
	private GenericListView list;

	public PIGUI (Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		background = tex;
		list = new GenericListView(new PIListModel(player));
		list.setHeight(155).setWidth(300).setX(edges.getLeft()+10).setY(edges.getTop()+20);
		guiLabel = new GenericLabel("PlayerInfo");
		guiLabel.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop());
		addChildren(new Widget[] {guiLabel, list});
		setWidth(0).setHeight(0);
	}
}
