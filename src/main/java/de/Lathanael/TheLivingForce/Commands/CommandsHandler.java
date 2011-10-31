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
 * along with SimpleCalc. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.TheLivingForce.Commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.Lathanael.TheLivingForce.Powers.BasePower;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class CommandsHandler implements CommandExecutor {

	private ForcePlugin plugin;
	private static CommandsHandler instance = null;
	private Set<Command> cmdList = new HashSet<Command>();

	public CommandsHandler(){
	}

	public CommandsHandler initInstance(ForcePlugin plugin) {
		this.plugin = plugin;
		if (instance == null)
			instance = new CommandsHandler();
		return instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}

	public void registerCommand(Class<? extends BasePower> class1) {
		BasePower cmd = null;
		try {
			cmd = (BasePower) class1.newInstance();
			plugin.getCommand(cmd.cmdName).setExecutor(instance);
			cmdList.add(plugin.getCommand(cmd.cmdName));
		} catch (InstantiationException e) {
			ForcePlugin.log.info("[TheLivingForce] Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ForcePlugin.log.info("[TheLivingForce] Could not create an Instance for: " + class1.getName());
			e.printStackTrace();
		}
	}

	public void registerCommand(String command) {
		cmdList.add(plugin.getCommand(command));
		plugin.getCommand(command).setExecutor(instance);
	}
}
