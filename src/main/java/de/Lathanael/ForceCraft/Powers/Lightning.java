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

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;
import de.Lathanael.ForceCraft.Utils.Scheduler;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
* @author Lathanael (aka Philippe Leipold)
*/
public class Lightning extends BasePower {

	public Lightning() {
		name = "Lightning";
		cmdName = "tlf_lightning";
		perm = "force.lightning";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment"));
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + ".delay");
		manaCost = instance.config.getInt("Power." + name + ".mana");
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(ForcePlayer player, Entity target) {
		if (target == null)
			return;
		player.increasePwrAmount(name);
		player.setLastTimeUsed(name, System.currentTimeMillis());
		if (target instanceof Player) {
			Player pTarget = (Player) target;
			if (pTarget == null)
				return;
			ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(pTarget.getName());
			if (fPlayer == null)
				return;
			fPlayer.setPowerState(PlayerPowerStates.SHOCKED);
			Scheduler.getInstance().scheduleLightningTask(player, fPlayer);
		} else if (target instanceof LivingEntity) {
			LivingEntity eTarget = (LivingEntity) target;
			if (eTarget == null)
				return;
			ForcePlugin.setStrokedEntity(eTarget);
			Scheduler.getInstance().scheduleLivingEntityLightningTask(player, eTarget);
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
