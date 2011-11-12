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

package de.Lathanael.ForceCraft.Powers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public abstract class BasePower {

	public String name = "";
	public String cmdName = "";
	public String perm = "";
	public ForceAlignment alignment = ForceAlignment.NEUTRAL;
	public int rank = 1;
	public final ForcePlugin instance;
	public Long delay = 0L;
	public int manaCost = 0;

	public BasePower() {
		instance = ForcePlugin.getInstance();
	}

	public abstract void execute(ForcePlayer player);

	public abstract boolean checkPerm(CommandSender sender);

	public boolean checkRank(ForcePlayer player) {
		if (player.getRank() < rank) {
			player.getHandler().sendMessage(ChatColor.RED +
					"You need to advance further in the Force to use this power!");
			return false;
		}
		return true;
	}
}
