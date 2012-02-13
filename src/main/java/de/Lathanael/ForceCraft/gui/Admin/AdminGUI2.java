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

import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;

import de.Lathanael.ForceCraft.gui.Geometry;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.PreviousButton;
import de.Lathanael.ForceCraft.gui.Admin.Buttons.ReloadButton;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class AdminGUI2 extends GenericContainer {

	public ReloadButton reloadButton;
	public PreviousButton prev;
	private Label adminLabel;
	public TextField output;

	public AdminGUI2 (Geometry edges, Texture background) {
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
		reloadButton = new ReloadButton("Reload");
		reloadButton.setHeight(15).setWidth(50).setX(edges.getLeft() + 10).setY(edges.getTop() + 45);
		reloadButton.setDirty(true);
		prev = new PreviousButton("Previous Page");
		prev.setHeight(15).setWidth(100).setX(edges.getLeft() + 10).setY(edges.getTop() + 160);
		prev.setDirty(true);
		addChildren(new Widget[] {adminLabel, prev, output, reloadButton});
		setWidth(0).setHeight(0);
	}
}
