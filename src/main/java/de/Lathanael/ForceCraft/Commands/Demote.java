package de.Lathanael.ForceCraft.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.Tools;

public class Demote extends BaseCommand {

	public Demote() {
		name = "force_demote";
		permNode = "force.demote";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ForcePlayer player = PlayerHandler.getInstance().getPlayer(args[0]);
		if (player == null)
			return;
		player.decRank();
		if (Tools.isPlayer(sender))
			sender.sendMessage(ChatColor.GREEN + "Demoted " + args[0]);
		else
			sender.sendMessage("[ForceCraft] Demoted " + args[0]);
	}

	@Override
	public boolean checkPerm(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender)
			return true;
		Player player = (Player) sender;
		if (PermissionsHandler.getInstance().hasPerm(player, permNode))
			return true;
		return false;
	}

	@Override
	public boolean checkArgs(String[] args) {
		return args.length >= 1;
	}

}
