package de.Lathanael.ForceCraft.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.Tools;

public class Promote extends BaseCommand {

	public Promote() {
		name = "fc_promote";
		permNode = "force.promote";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ForcePlayer player = PlayerHandler.getInstance().getPlayer(args[0]);
		if (player == null)
			return;
		player.incRank();
		if (Tools.isPlayer(sender))
			sender.sendMessage(ChatColor.GREEN + "Prmoted " + args[0]);
		else
			sender.sendMessage("[ForceCraft] Promoted " + args[0]);
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
