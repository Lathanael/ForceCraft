package de.Lathanael.TheLivingForce.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class CommandsHandler implements CommandExecutor {

	@SuppressWarnings("unused")
	private final ForcePlugin plugin;

	public CommandsHandler(ForcePlugin plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
