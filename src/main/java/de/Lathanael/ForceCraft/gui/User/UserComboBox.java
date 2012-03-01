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

package de.Lathanael.ForceCraft.gui.User;

import org.getspout.spoutapi.gui.ComboBox;
import org.getspout.spoutapi.gui.GenericComboBox;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class UserComboBox extends GenericComboBox implements ComboBox {

	private String selectedItem;

	/* (non-Javadoc)
	 * @see org.getspout.spoutapi.gui.GenericComboBox#onSelectionChanged(int, java.lang.String)
	 */
	@Override
	public void onSelectionChanged(int i, String text) {
		selectedItem = getSelectedItem();
		//this.setFormat("%selected%");
		this.setText(selectedItem);
		this.setDirty(true);
	}

	public String getSelectionChangedItem() {
		return selectedItem;
	}
}
