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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Lift extends BasePower {

	public Lift() {
		name = "Lift";
		cmdName = "fc_lift";
		perm = "force.lift";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment").toUpperCase());
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + ".delay");
		manaCost = instance.config.getInt("Power." + name + ".mana");
	}

	@Override
	public void execute(ForcePlayer player, Entity target) {
		if (target == null)
			return;
		if (target instanceof Player) {
			Player pTarget = (Player) target;
			pTarget.setVelocity(new Vector(0, 1, 0).normalize().multiply(0.75));
			Scheduler.getInstance().scheduleLiftTask(player,
					PlayerHandler.getInstance().getPlayer(pTarget.getName()));
			player.increasePwrAmount(name);
			player.setLastTimeUsed(name, System.currentTimeMillis());
			player.decMana(manaCost);
		} else if (target instanceof LivingEntity) {
			LivingEntity eTarget = (LivingEntity) target;
			eTarget.setVelocity(new Vector(0, 1, 0).normalize().multiply(0.75));
			Scheduler.getInstance().scheduleEntityLiftTask(player, eTarget);
			player.increasePwrAmount(name);
			player.setLastTimeUsed(name, System.currentTimeMillis());
			player.decMana(manaCost);
		}

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