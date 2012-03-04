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

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Heal extends BasePower {

	public Heal() {
		name = "Heal";
		cmdName = "fc_heal";
		perm = "force.heal";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment").toUpperCase());
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + ".delay");
		manaCost = instance.config.getInt("Power." + name + ".mana");
		costInc = instance.config.getInt("Power." + name + ".costInc");
	}

	@Override
	public int execute(ForcePlayer player, Entity target) {
		Player pTarget = null;
		if (target != null && target instanceof Player)
			pTarget = (Player) target;
		if (target == null) {
			Scheduler.getInstance().scheduleHealTask(player, player);
			Scheduler.getInstance().scheduleCancelHealTask(player);
		} else {
			Scheduler.getInstance().scheduleHealTask(player, PlayerHandler.getInstance().getPlayer(pTarget.getName()));
			Scheduler.getInstance().scheduleCancelHealTask(PlayerHandler.getInstance().getPlayer(pTarget.getName()));
		}
		player.increasePwrAmount(name);
		player.setLastTimeUsed(name, System.currentTimeMillis());
		int skillRank = player.getSkillRank(name);
		int cost = manaCost+costInc*(skillRank == 0 ? 0 : (skillRank - 1));
		player.decMana(cost);
		return cost;
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