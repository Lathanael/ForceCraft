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

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Ranks;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Set extends BaseCommand {


	public Set() {
		name = "fc_set";
		permNode = "force.set";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(args[0]);
		if (fPlayer == null)
			if (ForcePlugin.debug) {
				ForcePlugin.log.info("[ForceCraft] A ForcePlayer with the name "
						+ args[1] + " does not exist!");
				return;
			}
			else
				return;

		if (args.length >= 3) {
			if (args[1].equalsIgnoreCase("rank") && args.length >= 4) {
				int rank = Tools.parseInteger(sender, fPlayer, args[2], "rank");
				String side = args[3];
				fPlayer.setRank(Ranks.getRank(side, rank));
			} else if (args[1].equalsIgnoreCase("alignment")) {
				int al = Tools.parseInteger(sender, fPlayer, args[2], "alignment");
				fPlayer.setAlignment(ForceAlignment.getAlignmentByNr(al));
			} else if (args[1].equalsIgnoreCase("mana")) {
				int mana = Tools.parseInteger(sender, fPlayer, args[2], "mana");
				fPlayer.setMana(mana);
			}
		} else if (args[1].equalsIgnoreCase("reset")) {
			fPlayer.resetLastTimeUsed();
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
		return args.length >= 2;
	}

}
