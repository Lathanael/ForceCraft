/***************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of SimpleCalc.
 *
 * SimpleCalc is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SimpleCalc is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SimpleCalc. If not, see <http://www.gnu.org/licenses/>.
 *
 ***************************************************************************/

package de.Lathanael.ForceCraft.gui;

/**
* @author Lathanael (aka Philippe Leipold)
* https://github.com/Lathanael
**/

public class Geometry {
	private int left, right, top, bottom;

	public Geometry () {
	}

	public Geometry (int left, int right, int top, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int value) {
		this.left = value;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int value) {
		this.right = value;
	}
	public int getTop() {
		return top;
	}

	public void setTop(int value) {
		this.top = value;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int value) {
		this.bottom = value;
	}
}
