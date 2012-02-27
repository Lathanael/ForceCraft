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

package de.Lathanael.ForceCraft.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.Ranks;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Join extends BaseCommand {

	public Join() {
		name = "fc_join";
		permNode = "force.join";
	}
	/* (non-Javadoc)
	 * @see de.Lathanael.ForceCraft.Commands.BaseCommand#execute(org.bukkit.command.CommandSender, java.lang.String[])
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (Tools.isPlayer(sender, true)) {
			String side = args[0];
			ForcePlayer fPlayer;
			if (side.equalsIgnoreCase("light")) {
				fPlayer = PlayerHandler.getInstance().createNewForcePlayer(((Player) sender).getName());
				if (fPlayer == null) {
					Tools.debugMsg(ChatColor.RED + "Error while creating a new ForcePlayer for: " + ((Player) sender).getName(), sender, true);
					return;
				}
				Ranks rank = Ranks.getRank(side, 1);
				if (rank == Ranks.NONE) {
					Tools.debugMsg(ChatColor.RED + "Error while creating a new ForcePlayer for: " + ((Player) sender).getName(), sender, true);
					return;
				}
				fPlayer.setRank(rank);
			} else if (side.equalsIgnoreCase("dark")) {
				fPlayer = PlayerHandler.getInstance().createNewForcePlayer(((Player) sender).getName());
				if (fPlayer == null) {
					Tools.debugMsg(ChatColor.RED + "Error while creating the Rank for: " + ((Player) sender).getName(), sender);
					return;
				}
				Ranks rank = Ranks.getRank(side, 1);
				if (rank == Ranks.NONE) {
					Tools.debugMsg(ChatColor.RED + "Error while creating the Rank for: " + ((Player) sender).getName(), sender, true);
					return;
				}
				fPlayer.setRank(rank);
			} else
				sender.sendMessage(ChatColor.RED + "You must enter a correct side: Light or Dark!");
		}
	}

	/* (non-Javadoc)
	 * @see de.Lathanael.ForceCraft.Commands.BaseCommand#checkPerm(org.bukkit.command.CommandSender)
	 */
	@Override
	public boolean checkPerm(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender)
			return true;
		Player player = (Player) sender;
		if (PermissionsHandler.getInstance().hasPerm(player, permNode))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see de.Lathanael.ForceCraft.Commands.BaseCommand#checkArgs(java.lang.String[])
	 */
	@Override
	public boolean checkArgs(String[] args) {
		return args.length >= 1;
	}

}
