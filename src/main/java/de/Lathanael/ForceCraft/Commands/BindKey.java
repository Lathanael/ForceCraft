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

package de.Lathanael.ForceCraft.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.keyboard.Keyboard;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class BindKey extends BaseCommand {

	public BindKey() {
		name = "fc_bind";
		permNode = "force.bind";
	}
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Tools.isPlayer(sender, true))
			return;
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(((Player) sender).getName());
		if (fPlayer == null)
			return;
		try {
			int keyNr = Integer.parseInt(args[0]);
			Keyboard key = Tools.getKey(keyNr);
			if (key.equals(Keyboard.KEY_UNKNOWN)) {
				sender.sendMessage(ChatColor.RED + "You have entered an invalid key!");
				return;
			}
			fPlayer.setKey(key, args[1]);
			return;
		} catch (NumberFormatException e) {
			Keyboard key = Tools.getKey(args[0]);
			if (key.equals(Keyboard.KEY_UNKNOWN)) {
				sender.sendMessage(ChatColor.RED + "You have entered an invalid key!");
				return;
			}
			fPlayer.setKey(key, args[1]);
		}
	}

	@Override
	public boolean checkPerm(CommandSender sender) {
		return PermissionsHandler.getInstance().hasPerm(sender, permNode);
	}

	@Override
	public boolean checkArgs(String[] args) {
		return args.length >= 2;
	}

}
