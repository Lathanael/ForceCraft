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

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.Tools;

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
			if (Tools.isPlayer(sender))
				sender.sendMessage("Could not find a ForcePlayer with the name: " + args[0]);
			else
				sender.sendMessage(ChatColor.RED + "Could not find a ForcePlayer with the name: " + args[0]);
			return;
		}
		if (Tools.isPlayer(sender)) {
			sender.sendMessage(ChatColor.GREEN + "Information on ForcePlayer " + args[0] + ":");
			sender.sendMessage(ChatColor.GREEN + "Alignment: " + ChatColor.GOLD + player.getAlignment().toString());
			sender.sendMessage(ChatColor.GREEN + "Rank: " + ChatColor.GOLD + player.getRankName());
			sender.sendMessage(ChatColor.GREEN + "Mana: " + ChatColor.GOLD + String.valueOf(player.getMana()));
			sender.sendMessage(ChatColor.GREEN + "Power -- times used");
			for (Map.Entry<String, Integer> keys : player.getPowerAmounts().entrySet())
				sender.sendMessage(ChatColor.AQUA + keys.getKey() + ChatColor.GREEN + " -- "
						+ ChatColor.GOLD + keys.getValue());
		} else {
			sender.sendMessage("Information on ForcePlayer " + args[0] + ":");
			sender.sendMessage("Alignment: " + player.getAlignment().toString());
			sender.sendMessage("Rank: " + player.getRankName());
			sender.sendMessage("Mana: " + String.valueOf(player.getMana()));
			sender.sendMessage("Power -- times used");
			for (Map.Entry<String, Integer> keys : player.getPowerAmounts().entrySet())
				sender.sendMessage(keys.getKey() + " -- " + keys.getValue());
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
