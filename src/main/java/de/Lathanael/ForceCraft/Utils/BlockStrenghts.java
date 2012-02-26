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

package de.Lathanael.ForceCraft.Utils;

import java.util.HashSet;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class BlockStrenghts {

	public enum Low {
		DIRT, GRASS, LOG, SAND, GRAVEL, SPONGE, LEAVES, GLASS, WOOD, SANDSTONE, WEB, LONG_GRASS, DEAD_BUSH,
		WOOL, YELLOW_FLOWER, RED_ROSE, BROWN_MUSHROOM, RED_MUSHROOM, TORCH, CROPS, SOIL, LADDER, SNOW, ICE,
		SNOW_BLOCK, CACTUS, CLAY, SUGAR_CANE_BLOCK, PUMPKIN, NETHERRACK, SOUL_SAND, GLOWSTONE, HUGE_MUSHROOM_1,
		HUGE_MUSHROOM_2, THIN_GLASS, MELON_BLOCK, PUMPKIN_STEM, MELON_STEM, VINE, MYCEL, WATER_LILY, NETHER_WARTS;

		private static HashSet<String> blocks = new HashSet<String>();

		static {
			for (Low l : values()) {
				blocks.add(l.toString());
			}
		}

		public static boolean containsBlock(String blockName) {
			return blocks.contains(blockName);
		}
	}

	public enum Middle {
		LAPIS_BLOCK, LAPIS_ORE, COAL_ORE, IRON_ORE, GOLD_ORE, MOSSY_COBBLESTONE, WOOD_STAIRS, DIAMOND_ORE, WOODEN_DOOR,
		REDSTONE_ORE, GLOWING_REDSTONE_ORE, FENCE, JACK_O_LANTERN, TRAP_DOOR, MONSTER_EGGS, FENCE_GATE;

		private static HashSet<String> blocks = new HashSet<String>();

		static {
			for (Middle m : values()) {
				blocks.add(m.toString());
			}
		}

		public static boolean containsBlock(String blockName) {
			return blocks.contains(blockName);
		}
	}

	public enum High {
		BRICK, STONE_BRICK, COBBLESTONE, STONE, GOLD_BLOCK, IRON_BLOCK, DOUBLE_STEP, STEP, COBBLESTONE_STAIRS, SMOOTH_BRICK,
		IRON_FENCE, BRICK_STAIRS, SMOOTH_STAIRS, NETHER_BRICK, NETHER_FENCE, NETHER_BRICK_STAIRS;

		private static HashSet<String> blocks = new HashSet<String>();

		static {
			for (High h : values()) {
				blocks.add(h.toString());
			}
		}

		public static boolean containsBlock(String blockName) {
			return blocks.contains(blockName);
		}
	}

	public enum Indestructible {
		OBSIDIAN, BEDROCK, ENDER_STONE, NOTE_BLOCK, DISPENSER, BED_BLOCK, POWERED_RAIL, DETECTOR_RAIL, PISTON_STICKY_BASE,
		PISTON_BASE, PISTON_EXTENSION, PISTON_MOVING_PIECE, BOOKSHELF, MOB_SPAWNER, CHEST, REDSTONE_WIRE, DIAMOND_BLOCK,
		WORKBENCH, FURNACE, BURNING_FURNACE, RAILS, LEVER, STONE_PLATE, IRON_DOOR_BLOCK, WOOD_PLATE, REDSTONE_TORCH_OFF,
		REDSTONE_TORCH_ON, STONE_BUTTON, SIGN_POST, WALL_SIGN, JUKEBOX, PORTAL, CAKE_BLOCK, DIODE_BLOCK_OFF, DIODE_BLOCK_ON,
		LOCKED_CHEST, ENCHANTMENT_TABLE, BREWING_STAND, CAULDRON, ENDER_PORTAL, ENDER_PORTAL_FRAME, DRAGON_EGG;

		private static HashSet<String> blocks = new HashSet<String>();

		static {
			for (Indestructible i : values()) {
				blocks.add(i.toString());
			}
		}

		public static boolean containsBlock(String blockName) {
			return blocks.contains(blockName);
		}
	}

}
