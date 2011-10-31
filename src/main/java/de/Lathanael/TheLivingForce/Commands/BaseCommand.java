package de.Lathanael.TheLivingForce.Commands;

import org.bukkit.command.CommandSender;

public abstract class BaseCommand {

	public String name;
	public String permNode;

	public abstract void execute(CommandSender sender, String[] args);
}
