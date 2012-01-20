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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Utils.ForceAlignment;
import de.Lathanael.ForceCraft.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Pull extends BasePower {

	public Pull() {
		name = "Pull";
		cmdName = "fc_pull";
		perm = "force.pull";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment").toUpperCase());
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + ".delay");
		manaCost = instance.config.getInt("Power." + name + ".mana");
	}

	@Override
	public void execute(ForcePlayer player, Entity target) {
		Block block = (player.getHandler()).getTargetBlock(null, 20);
		if (block.getType().equals(Material.AIR) || block == null) {
			Tools.debugMsg("No Block was found or Block is an Air-Block!", player.getHandler());
			return;
		}
		int rank = player.getSkillRank(name);
		int amount = instance.ranksInfo.getInt(name + "." + String.valueOf(rank), 1);
		/*
		for (int i = 1; i < amount; i++) {
			Block nthBlock = null;
			if (facing.getX() >= facing.getZ())
				nthBlock = block.getRelative(i,0,0);
			else
				nthBlock = block.getRelative(0, 0, i);
			blocks.add(nthBlock);
		}
		Tools.moveBlocks(blocks, player.getHandler().getWorld(), false, facing);
		player.increasePwrAmount(name);
		player.setLastTimeUsed(name, System.currentTimeMillis());
		player.decMana(manaCost);
		*/
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