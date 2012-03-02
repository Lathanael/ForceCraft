/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import de.Lathanael.ForceCraft.Events.ManaChangeEvent;
import de.Lathanael.ForceCraft.Events.PowerUsedEvent;
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
	public long delay = 0L;
	public int manaCost = 0;
	public int costInc = 0;

	public BasePower() {
		instance = ForcePlugin.getInstance();
	}

	/**
	 * Executes the power and returns how much it did cost, or 0 if not executed
	 *
	 * @param player
	 * @param target
	 * @return
	 */
	public abstract int execute(ForcePlayer player, Entity target);

	public abstract boolean checkPerm(CommandSender sender);

	public boolean checkAlignment(ForcePlayer player) {
		ForceAlignment pa = player.getAlignment();
		if (alignment.equals(ForceAlignment.NEUTRAL))
			return true;
		else if (pa.equals(alignment))
			return true;
		else
			return false;
	}

	public boolean checkRank(ForcePlayer player) {
		if (player.getRank() < rank) {
			player.getHandler().sendMessage(ChatColor.RED +
					"You need to advance further in the Force to use Force " + name + " !");
			return false;
		}
		return true;
	}

	public boolean checkMana(ForcePlayer player) {
		if (player.getMana() < manaCost) {
			player.getHandler().sendMessage(ChatColor.RED +
					"You need more Mana to use Force " + name + " !");
			return false;
		}
		else if ((player.getMana() - manaCost) < 0) {
			player.getHandler().sendMessage(ChatColor.RED +
					"You need more Mana to use Force " + name + " !");
			return false;
		}
		return true;
	}

	public boolean checkTime(ForcePlayer player) {
		long time = 0;
		if (player.getLastTimeUsed(name) > 0) {
			time = (System.currentTimeMillis() - player.getLastTimeUsed(name))/1000;
		}
		if ((int) Math.round(delay) == 0 || (int) Math.round(time) == 0 || time >= delay) {
			return true;
		}
		player.getHandler().sendMessage(ChatColor.RED + "You need to wait to use Force " + name + " again!");
		return false;
	}

	public void issueEvents(ForcePlayer player, int mana, String powerName) {
		Bukkit.getServer().getPluginManager().callEvent(new PowerUsedEvent(player, powerName));
		Bukkit.getServer().getPluginManager().callEvent(new ManaChangeEvent(player, mana));
	}
}
