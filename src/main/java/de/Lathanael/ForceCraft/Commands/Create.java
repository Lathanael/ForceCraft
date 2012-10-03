/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
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

import de.Lathanael.ForceCraft.Players.PlayerHandler;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Create extends BaseCommand {

	public Create() {
		name = "fc_create";
		permNode = "force.create";
	}

	/* (non-Javadoc)
	 * @see de.Lathanael.ForceCraft.Commands.BaseCommand#execute(org.bukkit.command.CommandSender, java.lang.String[])
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		PlayerHandler.getInstance().createNewForcePlayer(args[0]);
		if (sender instanceof ConsoleCommandSender)
			sender.sendMessage("Successfully created ForcePlayer " + args[0]);
		else
			sender.sendMessage(ChatColor.GREEN + "Successfully created ForcePlayer "
					+ ChatColor.GOLD +  args[0]);
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
