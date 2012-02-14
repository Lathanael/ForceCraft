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
import de.Lathanael.ForceCraft.gui.Admin.Buttons.CreateButton;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.DemoteButton;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.NextButton;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.PlayerInfoButton;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.PromoteButton;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.SetButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class AdminGUI extends GenericContainer{

	@SuppressWarnings("unused")
	private SpoutPlayer player;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;
	private Label selPlayerLabel, adminLabel, chooseActionLabel;
	public ComboBox onlinePlayers, setListBox, setAlignmentBox, setRankBox;
	public TextField name, output, setField;
	public Button promote, demote, set, reload;
	public PlayerInfoButton info;
	public SetButton setButton;
	public CreateButton createButton;
	public DemoteButton demoteButton;
	public PromoteButton promoteButton;
	public NextButton next;

	public AdminGUI(Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		background = tex;
		adminLabel = new GenericLabel();
		adminLabel.setText("Admin GUI");
		adminLabel.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop() - 5);
		adminLabel.setDirty(true);
		output = new GenericTextField();
		output.setHeight(15).setWidth(300).setX(edges.getLeft()+10).setY(edges.getTop() + 20);
		output.setMaximumCharacters(25555);
		output.setMaximumLines(10);
		output.setEnabled(false);
		output.setDirty(true);
		selPlayerLabel = new GenericLabel();
		selPlayerLabel.setText("1. Select a player");
		selPlayerLabel.setHeight(10).setWidth(70).setX(edges.getLeft()+10).setY(edges.getTop() + 45);
		selPlayerLabel.setDirty(true);
		name = new GenericTextField();
		name.setHeight(15).setWidth(100).setX(edges.getLeft()+10).setY(edges.getTop() + 60);
		name.setMaximumCharacters(25555);
		name.setMaximumLines(10);
		name.setDirty(true);
		onlinePlayers = new AdminPlayerComboBox();
		onlinePlayers.setHeight(15).setWidth(100).setX(edges.getLeft() + 115).setY(edges.getTop() + 60);
		List<String> playerList = new ArrayList<String>();
		for (Player listPLayer : player.getServer().getOnlinePlayers()) {
			playerList.add(listPLayer.getName());
		}
		onlinePlayers.setItems(playerList);
		onlinePlayers.setText("Online Players");
		onlinePlayers.setDirty(true);
		chooseActionLabel = new GenericLabel();
		chooseActionLabel.setText("2. Choose an action to perform:");
		chooseActionLabel.setHeight(10).setWidth(150).setX(edges.getLeft()+10).setY(edges.getTop() + 80);
		chooseActionLabel.setDirty(true);
		info = new PlayerInfoButton("Force Player Info");
		info.setHeight(15).setWidth(95).setX(edges.getLeft()+10).setY(edges.getTop() + 100);
		info.setDirty(true);
		createButton = new CreateButton("Create Player");
		createButton.setTooltip("Creates a new ForcePlayer Object.");
		createButton.setHeight(15).setWidth(75).setX(edges.getLeft() + 110).setY(edges.getTop() + 100);
		createButton.setDirty(true);
		List<String> setList = new ArrayList<String>();
		setList.add("Mana");
		setList.add("MaxMana");
		setList.add("Alignment");
		setList.add("Rank");
		setListBox = new AdminComboBox();
		setListBox.setHeight(15).setWidth(75).setX(edges.getLeft() + 125).setY(edges.getTop() + 120);
		setListBox.setItems(setList);
		setListBox.setText("Select Type");
		setListBox.setDirty(true);
		setField = new GenericTextField();
		setField.setHeight(15).setWidth(100).setX(edges.getLeft() + 10).setY(edges.getTop() + 120);
		setField.setMaximumCharacters(25555);
		setField.setMaximumLines(10);
		setField.setDirty(true);
		List<String> setAlignmentList = new ArrayList<String>();
		setAlignmentList.add("Neutral");
		setAlignmentList.add("Light");
		setAlignmentList.add("Dark");
		setAlignmentBox = new AdminComboBox();
		setAlignmentBox.setHeight(15).setWidth(75).setX(edges.getLeft() + 205).setY(edges.getTop() + 120);
		setAlignmentBox.setItems(setAlignmentList);
		setAlignmentBox.setText("Select Alignment");
		setAlignmentBox.setDirty(true);
		List<String> setRankList = new ArrayList<String>();
		for (int i = 1; i < 6; i++)
			setRankList.add(String.valueOf(i));
		setRankBox = new AdminComboBox();
		setRankBox.setHeight(15).setWidth(75).setX(edges.getLeft() + 10).setY(edges.getTop() + 140);
		setRankBox.setItems(setRankList);
		setRankBox.setText("Select Rank");
		setRankBox.setDirty(true);
		setButton = new SetButton("Set...");
		setButton.setHeight(15).setWidth(50).setX(edges.getLeft() + 90).setY(edges.getTop() + 140);
		setButton.setDirty(true);
		promoteButton = new PromoteButton("Promote");
		promoteButton.setHeight(15).setWidth(50).setX(edges.getLeft() + 10).setY(edges.getTop() + 160);
		promoteButton.setDirty(true);
		demoteButton = new DemoteButton("Demote");
		demoteButton.setHeight(15).setWidth(50).setX(edges.getLeft() + 65).setY(edges.getTop() + 160);
		demoteButton.setDirty(true);
		next = new NextButton("Next Page");
		next.setHeight(15).setWidth(60).setX(edges.getLeft() + 250).setY(edges.getTop() + 160);
		next.setDirty(true);
		addChildren(new Widget[] {onlinePlayers, selPlayerLabel, name, output, adminLabel,
				info, chooseActionLabel, setListBox, setButton, setField, setAlignmentBox,
				setRankBox, createButton, demoteButton, promoteButton, next});
		setWidth(0).setHeight(0);
	}

	/**
	 * @param selectionChangedItem
	 */
	public void setInputText(String selectionChangedItem) {
		name.setText(selectionChangedItem);
		name.setDirty(true);
	}
}
