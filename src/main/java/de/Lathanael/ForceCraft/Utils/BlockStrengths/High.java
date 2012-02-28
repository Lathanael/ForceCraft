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

package de.Lathanael.ForceCraft.Utils.BlockStrengths;

import java.util.HashSet;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */

public enum High {
	BRICK(45), COBBLESTONE(4), STONE(1), GOLD_BLOCK(41), IRON_BLOCK(42), DOUBLE_STEP(43),
	STEP(44), COBBLESTONE_STAIRS(67), SMOOTH_BRICK(98), IRON_FENCE(101), BRICK_STAIRS(108), SMOOTH_STAIRS(109),
	NETHER_BRICK(112), NETHER_FENCE(113), NETHER_BRICK_STAIRS(114);

	private static HashSet<Integer> blocksH = new HashSet<Integer>();
	private int id;

	private High (int id) {
		this.id = id;
	}
	static {
		for (High h : values()) {
			blocksH.add(h.id);
		}
	}

	public static boolean containsBlock(int id) {
		return blocksH.contains(id);
	}
}