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

package de.Lathanael.TheLivingForce.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.TheLivingForce.Players.ForcePlayer;
import de.Lathanael.TheLivingForce.Players.PlayerHandler;
import de.Lathanael.TheLivingForce.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Info extends BaseCommand {

	public Info() {
		name = "force_info";
		permNode = "force.info";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ForcePlayer player = PlayerHandler.getInstance().getPlayer(args[0]);
		if (player == null) {
			if (Tools.isPLayer(sender, false))
				sender.sendMessage("Could not find a ForcePlayer with the name: " + args[0]);
			else
				sender.sendMessage(ChatColor.RED + "Could not find a ForcePlayer with the name: " + args[0]);
			return;
		}
		// TODO: Amounts-info
		if (Tools.isPLayer(sender, false)) {
			sender.sendMessage(ChatColor.GREEN + "Information on ForcePlayer " + args[0] + ":");
			sender.sendMessage(ChatColor.GREEN + "Alignment: " + ChatColor.GOLD + player.getAlignment().toString());
			sender.sendMessage(ChatColor.GREEN + "Rank: " + ChatColor.GOLD + player.getRankName());
			sender.sendMessage(ChatColor.GREEN + "Mana: " + ChatColor.GOLD + String.valueOf(player.getMana()));
		} else {
			sender.sendMessage("Information on ForcePlayer " + args[0] + ":");
			sender.sendMessage("Alignment: " + player.getAlignment().toString());
			sender.sendMessage("Rank: " + player.getRankName());
			sender.sendMessage("Mana: " + String.valueOf(player.getMana()));
		}
	}

	@Override
	public boolean checkPerm(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender)
			return true;
		Player player = (Player) sender;
		if (PermissionsHandler.getInstance().hasPerm(player, permNode))
			return true;
		return false;
	}

	@Override
	public boolean checkArgs(String[] args) {
		return args.length >= 1;
	}

}
