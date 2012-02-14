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

import java.util.Map;

import org.getspout.spoutapi.gui.AbstractListModel;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PIListModel extends AbstractListModel {

	private ListWidgetItem[] items;
	@SuppressWarnings("unused")
	private SpoutPlayer player;

	public PIListModel(SpoutPlayer player) {
		this.player = player;
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(player.getName());
		if (fPlayer == null) {
			items = new ListWidgetItem[0];
			items[0] = new ListWidgetItem("No ForcePlayer found",
					"Sorry could not retrieve a ForcePlayer associated to your name: " + player.getName());
			sizeChanged();
			return;
		}
		items = new ListWidgetItem[4+fPlayer.getPowerAmounts().size()];
		items[0] = new ListWidgetItem("Alignment", fPlayer.getAlignment().toString());
		items[1] = new ListWidgetItem("Rank", fPlayer.getRankName());
		items[2] = new ListWidgetItem("Mana", fPlayer.getMana() + "/" + fPlayer.getMaxMana());
		items[3] = new ListWidgetItem("Used/Available Skillpoints: ", fPlayer.getUsedSkillPoints()
				+ "/" + fPlayer.getAvailableSkillPoints());
		int i = 4;
		for (Map.Entry<String, Integer> keys : fPlayer.getPowerAmounts().entrySet()) {
			items[i] = new ListWidgetItem("Power -- times used", keys.getKey() + " -- " + keys.getValue());
			i++;
		}
		sizeChanged();
	}

	/* (non-Javadoc)
	 * @see org.getspout.spoutapi.gui.AbstractListModel#getItem(int)
	 */
	@Override
	public ListWidgetItem getItem(int row) {
		return items[row];
	}

	/* (non-Javadoc)
	 * @see org.getspout.spoutapi.gui.AbstractListModel#getSize()
	 */
	@Override
	public int getSize() {
		return items.length;
	}

	/* (non-Javadoc)
	 * @see org.getspout.spoutapi.gui.AbstractListModel#onSelected(int, boolean)
	 */
	@Override
	public void onSelected(int item, boolean doubleClick) {
	}

}
