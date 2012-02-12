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

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.gui.Admin.AdminComboBox;
import de.Lathanael.ForceCraft.gui.Admin.AdminGUI;
import de.Lathanael.ForceCraft.gui.PlayerInfo.PIGUI;
import de.Lathanael.ForceCraft.gui.SkillTree.SKGUI;
import de.Lathanael.ForceCraft.gui.User.UserGUI;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCUserInterface extends GenericPopup {

	private Button admin, skillTree, user, info;
	private Button close;
	private Texture background;
	private SpoutPlayer player;
	private Geometry edges = new Geometry();
	public static UserGUI userField;
	public static PIGUI infoField;
	public static AdminGUI adminField;
	public static SKGUI skillTreeField;

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
		close = new GenericButton(ChatColor.WHITE + "X");
		close.setAlign(WidgetAnchor.CENTER_CENTER);
		close.setHeight(10).setWidth(10).setX(edges.getRight()-10).setY(edges.getTop());
		close.setTooltip("Close the Window");
		attachWidget(ForcePlugin.getInstance(), close);
		user = new GenericButton(ChatColor.WHITE + "User GUI");;
		user.setHeight(15).setWidth(60).setX(edges.getRight()-70).setY(edges.getTop()+30);
		user.setTooltip("Opens the User GUI where HotKeys can be changed!");
		attachWidget(ForcePlugin.getInstance(), user);
		skillTree = new GenericButton(ChatColor.WHITE + "Skill Tree");
		skillTree.setHeight(15).setWidth(60).setX(edges.getRight()-70).setY(edges.getTop()+50);
		skillTree.setTooltip("Opens the Skill Tree");
		attachWidget(ForcePlugin.getInstance(), skillTree);
		if (PlayerHandler.getInstance().getPlayer(player.getName()).getRank() < 1)
			skillTree.setEnabled(false);
		admin = new GenericButton(ChatColor.WHITE + "Admin GUI");
		admin.setHeight(15).setWidth(60).setX(edges.getRight()-70).setY(edges.getTop()+70);
		admin.setTooltip("Opens the Admin GUI where the Admin can pro-/demote players etc.");
		attachWidget(ForcePlugin.getInstance(), admin);
		if (!player.hasPermission("force.admin"))
			admin.setEnabled(false);
		info = new GenericButton(ChatColor.WHITE + "PlayerInfo");
		info.setHeight(15).setWidth(60).setX(edges.getRight()-70).setY(edges.getTop()+90);
		info.setTooltip("Opens the Information view where you can see different things about your character.");
		attachWidget(ForcePlugin.getInstance(), info);
		userField = new UserGUI(edges, player, background);
		attachWidget(ForcePlugin.getInstance(), userField);
		infoField = new PIGUI(edges, player, background);
		attachWidget(ForcePlugin.getInstance(), infoField);
		adminField = new AdminGUI(edges, player, background);
		attachWidget(ForcePlugin.getInstance(), adminField);
		skillTreeField = new SKGUI(edges, player, background);
		attachWidget(ForcePlugin.getInstance(), skillTreeField);
	}

	public void openPlayerInfoField() {
		hideWidgets();
		infoField.setVisible(true);
		infoField.setDirty(true);
	}

	public void closePlayerInfoField() {
		infoField.setVisible(false);
		infoField.setDirty(true);
	}

	public void openUserGUI() {
		hideWidgets();
		userField.setVisible(true);
		userField.setDirty(true);
	}

	public void closeUserGUI() {
		userField.setVisible(false);
		userField.setDirty(true);
	}

	public void openAdminGUI() {
		hideWidgets();
		adminField.setVisible(true);
		adminField.setDirty(true);
	}

	public void closeAdminGUI() {
		adminField.setVisible(false);
		adminField.setDirty(true);
	}

	public void openSkillTreeGUI() {
		skillTreeField.setVisible(true);
		skillTreeField.setDirty(true);
	}

	public void closeSkillTreeGUI() {
		skillTreeField.setVisible(false);
		skillTreeField.setDirty(true);
	}

	public void open(){
		player.getMainScreen().attachPopupScreen(this);
		setDirty(true);
		hideWidgets();
		openPlayerInfoField();
	}

	public void hideWidgets() {
		closeUserGUI();
		closePlayerInfoField();
		closeAdminGUI();
		closeSkillTreeGUI();
	}

	public void onButtonClick(Button button) {
		if (button.equals(admin)) {
			openAdminGUI();
		} else if (button.equals(user)) {
			openUserGUI();
		} else if (button.equals(skillTree)) {

		} else if (button.equals(close)) {
			player.getMainScreen().closePopup();
		} else if (button.equals(info)) {
			openPlayerInfoField();
		} else if (button.equals(adminField.onlinePlayers)) {
			AdminComboBox box = (AdminComboBox) button;
			adminField.setInputText(box.getSelectionChangedItem());
		}
	}
}
