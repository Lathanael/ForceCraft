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

import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Reload extends BaseCommand {

	public Reload() {
		name = "fc_reload";
		permNode = "force.reload";
	}

	/* (non-Javadoc)
	 * @see de.Lathanael.ForceCraft.Commands.BaseCommand#execute(org.bukkit.command.CommandSender, java.lang.String[])
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ConsoleCommandSender)
			ForcePlugin.log.info("Reloading configuration files...");
		else
			sender.sendMessage(ChatColor.AQUA + "Reloading configuration files...");
		ForcePlugin.getInstance().reload();
		if (sender instanceof ConsoleCommandSender)
			ForcePlugin.log.info("Reloaded configuration files");
		else
			sender.sendMessage(ChatColor.GREEN + "Reloaded configuration files");
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
		return true;
	}

}
