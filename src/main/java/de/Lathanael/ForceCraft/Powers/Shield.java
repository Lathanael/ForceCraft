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

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Shield extends BasePower {

	public Shield() {
		name = "Shield";
		cmdName = "fc_shield";
		perm = "force.shield";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment").toUpperCase());
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + ".delay");
		manaCost = instance.config.getInt("Power." + name + ".mana");
		costInc = instance.config.getInt("Power." + name + ".costInc");
	}

	@Override
	public int execute(ForcePlayer player, Entity target) {
		player.setPowerState(PlayerPowerStates.SHIELD);
		Scheduler.getInstance().scheduleCancelShieldTask(player);
		SpoutPlayer sPlayer = (SpoutPlayer) player.getHandler();
		sPlayer.setDisplayName(ChatColor.AQUA + sPlayer.getDisplayName());
		player.increasePwrAmount(name);
		player.setLastTimeUsed(name, System.currentTimeMillis());
		player.decMana(manaCost+costInc*player.getSkillRank(name));
		return manaCost+costInc*player.getSkillRank(name);
	}

	@Override
	public boolean checkPerm(CommandSender sender) {
		if (!Tools.isPlayer(sender, true))
			return false;
		else if (PermissionsHandler.getInstance().hasPerm((Player) sender, perm))
			return true;
		return false;
	}
}