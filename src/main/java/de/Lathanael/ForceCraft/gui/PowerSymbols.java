/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
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

import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PowerSymbols extends GenericContainer {

	private Texture symb1, symb2, symb3, symb4, symb5;

	// TODO: setting up thos symbols!
	public PowerSymbols(SpoutPlayer player) {
		int screenWidth = player.getMainScreen().getWidth();
		symb1 = new GenericTexture();
		symb1.setHeight(15).setWidth(15).setY(0).setX(screenWidth-15);
		symb2 = new GenericTexture();
		symb2.setHeight(15).setWidth(15).setY(0).setX(screenWidth-30);
		symb3 = new GenericTexture();
		symb3.setHeight(15).setWidth(15).setY(0).setX(screenWidth-45);
		symb4 = new GenericTexture();
		symb4.setHeight(15).setWidth(15).setY(0).setX(screenWidth-60);
		symb5 = new GenericTexture();
		symb5.setHeight(15).setWidth(15).setY(0).setX(screenWidth-75);
		addChildren(new Widget[] {symb1, symb2, symb3, symb4, symb5});
		setWidth(0).setHeight(0);
	}

}