/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of TheLivingForce.
 *
 * TheLivingForce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TheLivingForce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TheLivingForce. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.TheLivingForce.Powers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.TheLivingForce.Commands.PermissionsHandler;
import de.Lathanael.TheLivingForce.Players.ForcePlayer;
import de.Lathanael.TheLivingForce.Utils.ForceAlignment;
import de.Lathanael.TheLivingForce.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Pull extends BasePower {

	public Pull() {
		name = "Pull";
		cmdName = "tlf_pull";
		perm = "force.pull";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment"));
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + ".delay");
		manaCost = instance.config.getInt("Power." + name + ".mana");
	}

	@Override
	public void execute(ForcePlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkPerm(CommandSender sender) {
		if (!Tools.isPLayer(sender, true))
			return false;
		else if (PermissionsHandler.getInstance().hasPerm((Player) sender, perm))
			return true;
		return false;
	}

}
