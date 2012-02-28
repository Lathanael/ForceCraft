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
public enum Low {
	DIRT(3), GRASS(4), LOG(17), SAND(12), GRAVEL(13), SPONGE(19), LEAVES(18), GLASS(20), WOOD(5), SANDSTONE(24), WEB(30),
	LONG_GRASS(31), DEAD_BUSH(32), WOOL(35), YELLOW_FLOWER(37), RED_ROSE(38), BROWN_MUSHROOM(39), RED_MUSHROOM(40),
	TORCH(50), CROPS(59), SOIL(60), LADDER(65), SNOW(78), ICE(79), SNOW_BLOCK(80), CACTUS(81), CLAY(82), SUGAR_CANE_BLOCK(83),
	PUMPKIN(86), NETHERRACK(87), SOUL_SAND(88), GLOWSTONE(89), HUGE_MUSHROOM_1(99), HUGE_MUSHROOM_2(100), THIN_GLASS(102),
	MELON_BLOCK(103), PUMPKIN_STEM(104), MELON_STEM(105), VINE(106), MYCEL(110), WATER_LILY(111), NETHER_WARTS(115);

	private static HashSet<Integer> blocksLow = new HashSet<Integer>();
	private int id;

	private Low(int id) {
		this.id = id;
	}

	static {
		for (Low l : values()) {
			blocksLow.add(l.id);
		}
	}

	public static boolean containsBlock(int id) {
		return blocksLow.contains(id);
	}
}
