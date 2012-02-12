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

package de.Lathanael.ForceCraft.gui.Admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.ComboBox;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.PlayerInfoButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class AdminGUI extends GenericContainer{

	@SuppressWarnings("unused")
	private SpoutPlayer player;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;
	private Label selPlayerLabel, adminLabel;
	public ComboBox onlinePlayers;
	public TextField input, output;
	public Button promote, demote, set, reload;
	public PlayerInfoButton info;

	public AdminGUI(Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		background = tex;
		adminLabel = new GenericLabel();
		adminLabel.setText("Admin GUI");
		adminLabel.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop());
		adminLabel.setDirty(true);
		output = new GenericTextField();
		output.setHeight(15).setWidth(300).setX(edges.getLeft()+10).setY(edges.getTop() + 20);
		output.setMaximumCharacters(25555);
		output.setMaximumLines(10);
		output.setEnabled(false);
		output.setDirty(true);
		selPlayerLabel = new GenericLabel();
		selPlayerLabel.setText("Select a player");
		selPlayerLabel.setHeight(10).setWidth(70).setX(edges.getLeft()+10).setY(edges.getTop() + 45);
		selPlayerLabel.setDirty(true);
		input = new GenericTextField();
		input.setHeight(15).setWidth(100).setX(edges.getLeft()+10).setY(edges.getTop() + 60);
		input.setMaximumCharacters(25555);
		input.setMaximumLines(10);
		input.setDirty(true);
		onlinePlayers = new AdminComboBox();
		onlinePlayers.setHeight(15).setWidth(100).setX(edges.getLeft() + 115).setY(edges.getTop() + 60);
		List<String> list = new ArrayList<String>();
		for (Player listPLayer : player.getServer().getOnlinePlayers()) {
			list.add(listPLayer.getName());
		}
		onlinePlayers.setItems(list);
		onlinePlayers.setText("Online Players");
		onlinePlayers.setDirty(true);
		info = new PlayerInfoButton("Force Player Info");
		info.setHeight(15).setWidth(95).setX(edges.getLeft()+10).setY(edges.getTop() + 100);
		info.setDirty(true);
		addChildren(new Widget[] {onlinePlayers, selPlayerLabel, input, output, adminLabel, info});
		setWidth(0).setHeight(0);
	}

	/**
	 * @param selectionChangedItem
	 */
	public void setInputText(String selectionChangedItem) {
		input.setText(selectionChangedItem);
		input.setDirty(true);
	}
}
