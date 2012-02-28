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
public enum Medium {
	LAPIS_BLOCK(22), LAPIS_ORE(21), COAL_ORE(16), IRON_ORE(15), GOLD_ORE(14), MOSSY_COBBLESTONE(48), WOOD_STAIRS(53),
	DIAMOND_ORE(56), WOODEN_DOOR(64), REDSTONE_ORE(73), GLOWING_REDSTONE_ORE(74), FENCE(85), JACK_O_LANTERN(91),
	TRAP_DOOR(96), MONSTER_EGGS(97), FENCE_GATE(107);

	private static HashSet<Integer> blocksM = new HashSet<Integer>();
	private int id;

	private Medium(int id) {
		this.id = id;
	}

	static {
		for (Medium m : values()) {
			blocksM.add(m.id);
		}
	}

	public static boolean containsBlock(int id) {
		return blocksM.contains(id);
	}
}
