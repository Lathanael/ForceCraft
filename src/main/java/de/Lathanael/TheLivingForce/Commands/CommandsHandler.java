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

package de.Lathanael.TheLivingForce.Commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.TheLivingForce.Players.PlayerHandler;
import de.Lathanael.TheLivingForce.Powers.BasePower;
import de.Lathanael.TheLivingForce.Utils.Tools;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;
import de.Lathanael.TheLivingForce.Commands.BaseCommand;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 * Some code-ideas in here are from AdminCmd!
 */
public class CommandsHandler implements CommandExecutor {

	private ForcePlugin plugin;
	private static CommandsHandler instance = null;
	private HashMap<Command, BasePower> powerMap = new HashMap<Command, BasePower>();
	private HashMap<Command, BaseCommand> cmdMap = new HashMap<Command, BaseCommand>();

	public CommandsHandler(){
	}

	public CommandsHandler initInstance(ForcePlugin plugin) {
		this.plugin = plugin;
		if (instance == null)
			instance = new CommandsHandler();
		return instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (powerMap.get(cmd) != null) {
			executePower(sender, powerMap.get(cmd));
			return true;
		} else if (cmdMap.get(cmd) != null) {
			executeCommand(sender, cmdMap.get(cmd), args);
			return true;
		}
		return false;
	}

	private void executePower(CommandSender sender, BasePower power) {
		if (Tools.isPLayer(sender, true)) {
			if (PermissionsHandler.getInstance().hasPerm((Player) sender, power.perm))
				power.execute(PlayerHandler.getInstance().getPlayer(((Player) sender).getName()));
			else
				sender.sendMessage(ChatColor.RED + "You do not have the permission to use the " + power.name + " Power!");
		}
	}

	private void executeCommand(CommandSender sender, BaseCommand cmd, String[] args) {
	}

	public void registerPower(Class<? extends BasePower> class1) {
		BasePower power = null;
		try {
			power = (BasePower) class1.newInstance();
			plugin.getCommand(power.cmdName).setExecutor(instance);
			powerMap.put(plugin.getCommand(power.cmdName), power);
		} catch (InstantiationException e) {
			ForcePlugin.log.info("[TheLivingForce] Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ForcePlugin.log.info("[TheLivingForce] Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		}
	}

	public void registerCommand(Class<? extends BaseCommand> class1) {
		BaseCommand cmd = null;
		try {
			cmd = (BaseCommand) class1.newInstance();
			plugin.getCommand(cmd.name).setExecutor(instance);
			cmdMap.put(plugin.getCommand(cmd.name), cmd);
		} catch (InstantiationException e) {
			ForcePlugin.log.info("[TheLivingForce] Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ForcePlugin.log.info("[TheLivingForce] Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		}
	}
}
