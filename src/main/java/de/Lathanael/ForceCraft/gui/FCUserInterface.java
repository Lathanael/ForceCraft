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

package de.Lathanael.ForceCraft.gui;

import java.util.Map;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCUserInterface extends GenericPopup {

	private Button admin, skillTree, user, info;
	private Button close;
	private Texture background;
	private Label guiLabel;
	private TextField playerInfo;
	private SpoutPlayer player;
	private Geometry edges = new Geometry();
	private UserGUI userField;

	public FCUserInterface(SpoutPlayer player) {
		this.player = player;
		int screenWidth = player.getMainScreen().getWidth();
		int screenHeight = player.getMainScreen().getHeight();
		background = new GenericTexture("http://dl.dropbox.com/u/42731731/Background.png");
		background.setHeight(200).setWidth(400).setX((screenWidth - 400)/2).setY((screenHeight-200)/2);
		background.setPriority(RenderPriority.Highest);
		edges.setLeft((screenWidth-390)/2);
		edges.setRight(background.getX()+background.getWidth()-10);
		edges.setTop(background.getY()+10);
		edges.setBottom(background.getHeight()+background.getY()-10);
		attachWidget(ForcePlugin.getInstance(), background);
		guiLabel = new GenericLabel("PlayerInfo");
		guiLabel.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop());
		attachWidget(ForcePlugin.getInstance(), guiLabel);
		close = new GenericButton(ChatColor.WHITE + "X");
		close.setAlign(WidgetAnchor.CENTER_CENTER);
		close.setHeight(10).setWidth(10).setX(edges.getRight()-10).setY(edges.getTop());
		close.setTooltip("Close the Window");
		attachWidget(ForcePlugin.getInstance(), close);
		user = new GenericButton(ChatColor.WHITE + "User GUI");;
		user.setHeight(10).setWidth(50).setX(edges.getRight()-65).setY(edges.getTop()+30);
		user.setTooltip("Opens the User GUI where HotKeys can be changed!");
		attachWidget(ForcePlugin.getInstance(), user);
		skillTree = new GenericButton(ChatColor.WHITE + "Skill Tree");
		skillTree.setHeight(10).setWidth(50).setX(edges.getRight()-65).setY(edges.getTop()+50);
		skillTree.setTooltip("Opens the Skill Tree");
		attachWidget(ForcePlugin.getInstance(), skillTree);
		if (PlayerHandler.getInstance().getPlayer(player.getName()).getRank() < 1)
			skillTree.setEnabled(false);
		admin = new GenericButton(ChatColor.WHITE + "Admin GUI");
		admin.setHeight(10).setWidth(50).setX(edges.getRight()-65).setY(edges.getTop()+70);
		admin.setTooltip("Opens the Admin GUI where the Admin can pro-/demote players etc.");
		attachWidget(ForcePlugin.getInstance(), admin);
		if (!player.hasPermission("force.admin"))
			admin.setEnabled(false);
		info = new GenericButton(ChatColor.WHITE + "Player Information");
		info.setHeight(10).setWidth(50).setX(edges.getRight()-65).setY(edges.getTop()+90);
		info.setTooltip("Opens the Information view where you can see different things about your character.");
		attachWidget(ForcePlugin.getInstance(), info);
		createPlayerInfoField();
		attachWidget(ForcePlugin.getInstance(), playerInfo);
		userField = new UserGUI(edges, player, background);
		attachWidget(ForcePlugin.getInstance(), userField);
	}

	private void createPlayerInfoField() {
		playerInfo = new GenericTextField();
		playerInfo.setHeight(155).setWidth(300).setX(edges.getLeft()+10).setY(edges.getTop()+20);
		playerInfo.setEnabled(false);
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(player.getName());
		String eol = System.getProperty("line.separator");
		String result = "Alignment: " + fPlayer.getAlignment().toString() + eol;
		result = result + "Rank: " + fPlayer.getRankName() + eol;
		result = result + "Mana: " + fPlayer.getMana() + "/" + fPlayer.getMaxMana() + eol;
		result = result + "Power -- times used" + eol;
		for (Map.Entry<String, Integer> keys : fPlayer.getPowerAmounts().entrySet())
			result = result + keys.getKey() + " -- " + keys.getValue() + eol;
		playerInfo.setMaximumCharacters(5000);
		playerInfo.setMaximumLines(25);
		playerInfo.setText(result);
	}

	public void openPlayerInfoField() {
		hideWidgets();
		playerInfo.setVisible(true);
		playerInfo.setDirty(true);
		guiLabel.setVisible(true);
		guiLabel.setDirty(true);
	}

	public void openUserGUI() {
		userField.setVisible(true);
		userField.setDirty(true);
	}

	public void closeUserGUI() {
		userField.setVisible(false);
		userField.setDirty(true);
	}

	public void open(){
		player.getMainScreen().attachPopupScreen(this);
		setDirty(true);
		hideWidgets();
		playerInfo.setVisible(true);
		playerInfo.setDirty(true);
		guiLabel.setVisible(true);
		guiLabel.setDirty(true);
	}

	public void hideWidgets() {
		closeUserGUI();
		playerInfo.setVisible(false);
		playerInfo.setDirty(true);
		guiLabel.setVisible(false);
		guiLabel.setDirty(true);
	}

	public void onButtonClick(Button button) {
		if (button.equals(admin)) {

		} else if (button.equals(user)) {
			openUserGUI();
		} else if (button.equals(skillTree)) {

		} else if (button.equals(close)) {
			player.getMainScreen().closePopup();
		} else if (button.equals(info)) {
			openPlayerInfoField();
		}
	}
}
