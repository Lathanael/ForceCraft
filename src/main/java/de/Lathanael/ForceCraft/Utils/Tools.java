/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
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

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.keyboard.Keyboard;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 * Some code-ideas in here are from AdminCmd!
 */
public class Tools {

	/**
	 * Gets the Spout Keyboard.KEY Enum corresponding to the given key-Integer
	 * @param keyNr
	 * @return
	 */
	public static Keyboard getKey(int keyNr) {
		return Keyboard.getKey(keyNr);
	}

	/**
	 * Gets the Spout Keyboard.KEY Enum corresponding to the given key-String
	 * @param keyName
	 * @return
	 */
	public static Keyboard getKey(String keyName) {
		return Keyboard.valueOf("KEY_" + keyName.toUpperCase());
	}

	/**
	 * Checks if a CommandSender instance is an instance of a Player instance.
	 *
	 * @param sender - The CommandSender to be checked
	 * @param errorMsg - Should an error Message be displayed?
	 * @return True if it is a Player instance, else false.
	 */
	public static boolean isPlayer(CommandSender sender, boolean errorMsg) {
		if (sender instanceof Player)
			return true;
		else {
			if (errorMsg)
				sender.sendMessage("[ForceCraft] You must be a player to use this command!");
			return false;
		}
	}

	/**
	 * @see Tools#isPlayer(CommandSender, boolean) isPlayer(sender, false)
	 */
	public static boolean isPlayer(CommandSender sender) {
		return isPlayer(sender, false);
	}

	/**
	 * Gets the targeted Entity a player is facing!
	 * The entity is not guaranteed to be a Player!
	 *
	 * @param block - The block the Player is looking at
	 * @param mustBePlayer - Must the entity returned be a Player?
	 * @return The entity found or null if no entity found.
	 */
	public static Entity getTargetedEntity(Block block, boolean mustBePlayer) {
		if (block == null)
			return null;
		Entity[] list = block.getChunk().getEntities();
		if (list.length > 0)
			for (Entity entity : list)
				if (entity instanceof LivingEntity) {
					LivingEntity lEntity = (LivingEntity) entity;
					LivingEntity target = null;
					Block newBlock = block;
					for (int i = -1; i < 1; i++) {
						newBlock.getLocation().setY(block.getLocation().getY() + i);
						if (checkDistance(entity.getLocation(), newBlock.getLocation(), 1))
							target = lEntity;
					}
					if (target == null)
						return target;
					if (mustBePlayer)
						if (target instanceof Player)
							return target;
						else
							return null;
					else
						return target;
				}
		return null;
	}

	/**
	 * Parses and returns a given String s to an Integer, default value depends on type.
	 *
	 * @param sender - The Command sender
	 * @param player - The ForcePlayer object whose value should be set
	 * @param s - The String to be parsed
	 * @param type - String to define the default value
	 * @return The parsed Integer or default value of the given type
	 * (if no matching type is found 0) if failed.
	 */
	public static int parseInteger(CommandSender sender, ForcePlayer player, String s, String type) {
		int value = 0;

		try {
			value = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			if (Tools.isPlayer(sender))
				sender.sendMessage(ChatColor.RED + "Could not parse the provided number!");
			else
				sender.sendMessage("[ForceCraft] Could not parse the provided number!");

			if (type.equalsIgnoreCase("rank"))
				value = player.getRank();
			else if (type.equalsIgnoreCase("alignment"))
				value = player.getAlignment().getNumber();
			else if (type.equalsIgnoreCase("mana"))
				value = player.getMana();
			else
				value = 0;
		}
		return value;
	}

	/**
	 * Print a Debug Message to the console and if command/power preformed by a player to the player!
	 *
	 * @param message - The message to be printed
	 * @param sender - The Object which did call the command/power, null if none!
	 */
	public static void debugMsg(String message, CommandSender sender) {
		if (!ForcePlugin.debug)
			return;
		if (sender == null)
			ForcePlugin.log.info("[ForceCraft] " + message);
		else if (sender instanceof ConsoleCommandSender){
			ForcePlugin.log.info("[ForceCraft] " + message);
		}
		else {
			ForcePlugin.log.info("[ForceCraft] " + message);
			sender.sendMessage(ChatColor.RED + message);
		}
	}

	/**
	 * Checks if one entity is within the range of another one (in the xz-plane)
	 *
	 * @param ent1 - First Entity
	 * @param ent2 - Second Entity
	 * @param checkDist - The maximum distance they are allowed to be apart
	 * @return True if (dist < checkDist) else false
	 */
	public static boolean checkDistance (Entity ent1, Entity ent2, double checkDist, CommandSender sender) {
		if (ent1 == null) {
			debugMsg("NPE in checkDistance, Entity 1 is null.", sender);
			return false;
		} else if (ent2 == null) {
			debugMsg("NPE in checkDistance, Entity 2 is null.", sender);
			return false;
		}
		if (!ent1.getWorld().equals(ent2.getWorld()))
			return false;
		Location loc1 = ent1.getLocation();
		Location loc2 = ent2.getLocation();
		double dist = Math.sqrt(Math.pow((loc1.getX() - loc2.getX()), 2) + Math.pow((loc1.getZ() - loc2.getZ()), 2)
				+ Math.pow(loc1.getY() - loc2.getY(), 2));
		if (dist > checkDist)
			return true;
		return false;
	}

	public static boolean checkDistance (Location loc1, Location loc2, double checkDist) {
		if (loc1 == null) {
			debugMsg("NPE in checkDistance, Location 1 is null.", null);
			return false;
		} else if (loc2 == null) {
			debugMsg("NPE in checkDistance, Location 2 is null.", null);
			return false;
		}
		double dist = Math.sqrt(Math.pow((loc1.getX() - loc2.getX()), 2) + Math.pow((loc1.getZ() - loc2.getZ()), 2)
				+ Math.pow(loc1.getY() - loc2.getY(), 2));
		if (dist > checkDist)
			return true;
		return false;
	}

	/**
	 * Moves a set of Blocks forward or backward. If the blocks hit another one they stop!
	 * @param blocks
	 * @param pushing
	 */
	public static void moveBlocks(List<Block> blocks, World world, boolean pushing, Vector direction) {
		int x = (int) Math.abs(direction.getX());
		double z = (int) Math.abs(direction.getZ());
		int length = blocks.size();
		int counter = 0;
		if (pushing) {

		} else {
			for (Block block : blocks) {
				Location loc = block.getRelative(length-counter, 0, 0).getLocation();
				counter++;
			}
		}
	}

	public static void savePlayerFiles (HashMap<String, ForcePlayer> onlinePlayers) {

	}
}
