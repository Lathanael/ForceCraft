/*************************************************************************
 * Copyright (C) 2011-2012  Philippe Leipold
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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Demote extends BaseCommand {

	public Demote() {
		name = "fc_demote";
		permNode = "force.demote";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ForcePlayer player = PlayerHandler.getInstance().getPlayer(args[0]);
		if (player == null)
			return;
		player.decRank();
		if (Tools.isPlayer(sender))
			sender.sendMessage(ChatColor.GREEN + "Demoted " + args[0]);
		else
			sender.sendMessage("[ForceCraft] Demoted " + args[0]);
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
