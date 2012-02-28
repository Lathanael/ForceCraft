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
public enum Indestructible {
	OBSIDIAN(49), BEDROCK(7), ENDER_STONE(121), NOTE_BLOCK(25), DISPENSER(23), BED_BLOCK(26), POWERED_RAIL(27), DETECTOR_RAIL(28),
	PISTON_STICKY_BASE(29), PISTON_BASE(33), PISTON_EXTENSION(33), PISTON_MOVING_PIECE(36), BOOKSHELF(47), MOB_SPAWNER(52),
	CHEST(54), REDSTONE_WIRE(55), DIAMOND_BLOCK(57), WORKBENCH(58), FURNACE(61), BURNING_FURNACE(62), RAILS(66), LEVER(69),
	STONE_PLATE(70), IRON_DOOR_BLOCK(71), WOOD_PLATE(72), REDSTONE_TORCH_OFF(75), REDSTONE_TORCH_ON(76), STONE_BUTTON(77),
	SIGN_POST(63), WALL_SIGN(68), JUKEBOX(84), PORTAL(90), CAKE_BLOCK(92), DIODE_BLOCK_OFF(93), DIODE_BLOCK_ON(94), LOCKED_CHEST(95),
	ENCHANTMENT_TABLE(116), BREWING_STAND(117), CAULDRON(118), ENDER_PORTAL(119), ENDER_PORTAL_FRAME(120), DRAGON_EGG(122), TNT(46);

	private static HashSet<Integer> blocksI = new HashSet<Integer>();
	private int id;

	private Indestructible(int id) {
		this.id = id;
	}

	static {
		for (Indestructible i : values()) {
			blocksI.add(i.id);
		}
	}

	public static boolean containsBlock(int id) {
		return blocksI.contains(id);
	}
}