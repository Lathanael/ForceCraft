/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of TheLivingForce.
 *
 * TheLivingForce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TheLivingForce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TheLivingForce. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.ForceCraft.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.keyboard.Keyboard;

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
				sender.sendMessage("[TheLivingForce] You must be a player to use this command!");
			return false;
		}
	}

	/**
	 * @see Tools#isPlayer(CommandSender, boolean) isPlayer
	 */
	public static boolean isPlayer(CommandSender sender) {
		return isPlayer(sender, false);
	}

	/**
	 * Gets the targeted Entity a player is facing!
	 */
	public static Player getTargetedEntity(Player player) {
		return null;
	}

	/**
	 * Gets the targeted Block a player is facing!
	 */
	public static Player getTargetedBlock(Player player) {
		return null;
	}
}
