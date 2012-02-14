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

package de.Lathanael.ForceCraft.gui.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.gui.ComboBox;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.PowerList;
import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.User.Buttons.BindKeyButton;
import de.Lathanael.ForceCraft.gui.User.Buttons.RemoveKeyButton;
import de.Lathanael.ForceCraft.gui.User.Buttons.ResetKeysButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class UserGUI extends GenericContainer {
	private TextField keys;
	private Label userLabel;
	public BindKeyButton changeKey;
	public RemoveKeyButton removeKey;
	public ResetKeysButton resetKeys;
	public ComboBox availableKeys, powers;
	@SuppressWarnings("unused")
	private SpoutPlayer player;
	private ForcePlayer fPlayer;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;

	public UserGUI(Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		fPlayer = PlayerHandler.getInstance().getPlayer(player.getName());
		background = tex;
		userLabel = new GenericLabel();
		userLabel.setText("User GUI");
		userLabel.setHeight(10).setWidth(50).setX(edges.getLeft()+background.getWidth()/2-100).setY(edges.getTop() - 5);
		userLabel.setDirty(true);
		keys = new GenericTextField();
		keys.setHeight(55).setWidth(300).setX(edges.getLeft()+10).setY(edges.getTop()+20);
		keys.setEnabled(false);
		String result = "";
		if (fPlayer != null) {
			result = "Key    ---  Power\n";
			for (Map.Entry<Keyboard, String> entries : fPlayer.getKeys().entrySet()) {
				result += entries.getKey().toString() + "  ---  " + entries.getValue() + "\n";
			}
		} else {
			result = "Sorry no ForcePlayer associated to your name could be found.";
		}
		keys.setMaximumCharacters(25555);
		keys.setMaximumLines(10);
		keys.setText(result);
		keys.setDirty(true);
		availableKeys = new UserComboBox();
		availableKeys.setHeight(15).setWidth(100).setX(edges.getLeft()+10).setY(edges.getTop() + 80);
		availableKeys.setText("Select a Key");
		List<String> keyList = new ArrayList<String>();
		for (Keyboard key : Keyboard.values()) {
			keyList.add(key.toString());
		}
		availableKeys.setItems(keyList);
		availableKeys.setDirty(true);
		List<String> powerList = new ArrayList<String>();
		powers = new UserComboBox();
		powers.setHeight(15).setWidth(100).setX(edges.getLeft()+115).setY(edges.getTop() + 80);
		powers.setText("Select a Power");
		for (PowerList power : PowerList.values()) {
			powerList.add(power.getString());
		}
		powers.setItems(powerList);
		powers.setDirty(true);
		changeKey = new BindKeyButton("Bind Key");
		changeKey.setHeight(15).setWidth(60).setX(edges.getLeft()+10).setY(edges.getTop() + 100);
		changeKey.setDirty(true);
		resetKeys = new ResetKeysButton("Reset keys");
		resetKeys.setHeight(15).setWidth(60).setX(edges.getLeft()+80).setY(edges.getTop() + 100);
		resetKeys.setDirty(true);
		removeKey = new RemoveKeyButton("Remove key");
		removeKey.setHeight(15).setWidth(60).setX(edges.getLeft()+150).setY(edges.getTop() + 100);
		removeKey.setDirty(true);
		addChildren(new Widget[] {keys, userLabel, changeKey, availableKeys, powers, resetKeys, removeKey});
		setWidth(0).setHeight(0);
	}

	public void updateTextField() {
		String result = "Key    ---  Power\n";
		for (Map.Entry<Keyboard, String> entries : fPlayer.getKeys().entrySet()) {
			result += entries.getKey().toString() + "  ---  " + entries.getValue() + "\n";
		}
		keys.setText(result);
		keys.setDirty(true);
	}
}
