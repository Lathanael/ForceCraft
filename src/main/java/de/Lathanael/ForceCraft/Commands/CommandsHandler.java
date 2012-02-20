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

package de.Lathanael.ForceCraft.Commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Commands.BaseCommand;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Powers.BasePower;
import de.Lathanael.ForceCraft.Utils.Tools;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 * Some code-ideas in here are from AdminCmd!
 */
public class CommandsHandler implements CommandExecutor {

	private static ForcePlugin plugin;
	private static CommandsHandler instance = null;
	private HashMap<Command, BasePower> powerMap = new HashMap<Command, BasePower>();
	private HashMap<Command, BaseCommand> cmdMap = new HashMap<Command, BaseCommand>();

	public CommandsHandler(){
	}

	public static CommandsHandler initInstance(ForcePlugin fplugin) {
		plugin = fplugin;
		if (instance == null)
			instance = new CommandsHandler();
		return instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (powerMap.get(cmd) != null) {
			if (args.length > 0) {
				Player player = plugin.getServer().getPlayer(args[0]);
				if (player == null)
					executePower(sender, powerMap.get(cmd), null);
				else
					executePower(sender, powerMap.get(cmd), player);
			} else
				executePower(sender, powerMap.get(cmd), null);
			return true;
		} else if (cmdMap.get(cmd) != null) {
			return executeCommand(sender, cmdMap.get(cmd), args);
		}
		return false;
	}

	public void executePower(CommandSender sender, BasePower power, Entity target) {
		if (Tools.isPlayer(sender, true)) {
			if (PermissionsHandler.getInstance().hasPerm((Player) sender, power.perm)) {
				ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(((Player) sender).getName());
				if (fPlayer == null) {
					sender.sendMessage(ChatColor.RED + "Could not find your ForcePlayer object, please contact your ServerAdmin with this error!");
					return;
				}
				if (power.checkRank(fPlayer) && power.checkTime(fPlayer) && power.checkMana(fPlayer)) {
					Player executor = (Player) sender;
					Block block = null;
					int cost = 0;
					if (power.name.equalsIgnoreCase("pull")) {
						block = executor.getTargetBlock(null, ForcePlugin.checkDist);
						if (block == null)
							return;
						else if (Tools.checkDistance(executor.getLocation(), block.getLocation(), ForcePlugin.checkDist, executor)){
							cost = power.execute(fPlayer, target);
							power.issueEvents(fPlayer, cost, power.name);
						}
					}
					else if (power.name.equalsIgnoreCase("push")) {
						block = executor.getTargetBlock(null, ForcePlugin.checkDist);
						if (block == null)
							return;
						else if (Tools.checkDistance(executor.getLocation(), block.getLocation(), ForcePlugin.checkDist, executor)) {
							cost = power.execute(fPlayer, target);
							power.issueEvents(fPlayer, cost, power.name);
						}
					}
					else if (Tools.checkDistance(executor, target, ForcePlugin.checkDist, executor)) {
						cost = power.execute(fPlayer, target);
						power.issueEvents(fPlayer, cost, power.name);
					}
				}

			}
			else
				sender.sendMessage(ChatColor.RED + "You do not have the permission to use the following power: "
						+ ChatColor.AQUA + power.name + ChatColor.RED + "!");
		}
	}

	private boolean executeCommand(CommandSender sender, BaseCommand cmd, String[] args) {
		if (PermissionsHandler.getInstance().hasPerm(sender, cmd.permNode)) {
			if (cmd.checkArgs(args)) {
				cmd.execute(sender, args);
				return true;
			} else
				return false;
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the permission to use the following command: "
					+ ChatColor.AQUA + cmd.name + ChatColor.RED + "!");
			return true;
		}
	}

	public void registerPower(Class<? extends BasePower> class1) {
		BasePower power = null;
		try {
			power = (BasePower) class1.newInstance();
			plugin.getCommand(power.cmdName).setExecutor(instance);
			powerMap.put(plugin.getCommand(power.cmdName), power);
		} catch (InstantiationException e) {
			ForcePlugin.log.info("Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ForcePlugin.log.info("Could not create an Instance for: " + class1.getName());
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
			ForcePlugin.log.info("Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ForcePlugin.log.info("Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		}
	}

	public BasePower getPower(Command cmd) {
		return powerMap.get(cmd);
	}

	public BaseCommand getCmd(Command cmd) {
		return cmdMap.get(cmd);
	}
}
