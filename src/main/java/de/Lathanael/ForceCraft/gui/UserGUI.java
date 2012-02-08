/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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

package de.Lathanael.ForceCraft.gui;

import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class UserGUI extends GenericContainer {
	private TextField key;
	private Label userLabel;
	private Label key1, ke2, ke3;
	private Button changeKey;
	private SpoutPlayer player;
	private ForcePlayer fPlayer;
	private Geometry edges;
	private Texture background;

	public UserGUI(Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		fPlayer = PlayerHandler.getInstance().getPlayer(player.getName());
		background = tex;
		userLabel = new GenericLabel();
		userLabel.setText("User GUI");
		userLabel.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop());
		userLabel.setDirty(true);
		key = new GenericTextField();
		key.setHeight(10).setWidth(250).setX(edges.getLeft()+10).setY(edges.getBottom() - 30);
		key.setDirty(true);
		changeKey = new GenericButton("Change Key");
		changeKey.setHeight(10).setWidth(40).setX(edges.getLeft()+265).setY(edges.getBottom() -30);
		changeKey.setDirty(true);
		fPlayer.getKeys();
		key1 = new GenericLabel("");
		addChildren(new Widget[] {userLabel, key, changeKey});
		setWidth(0).setHeight(0);
	}

}
