package com.Nips.TnTSlap.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.Config.ArenaConfig;

public class SetLobbyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		if (player.isOp() == true) {
			if (args.length == 0) {
				// player.sendMessage(ChatColor.LIGHT_PURPLE + "  ======  " + ChatColor.YELLOW + "" + ChatColor.BOLD + "Arena Commands" + ChatColor.LIGHT_PURPLE + "  ======  ");
				// player.sendMessage(ChatColor.LIGHT_PURPLE + "Create" + ChatColor.YELLOW + "     '/arena create <name>' to create a new arena");
				// player.sendMessage(ChatColor.LIGHT_PURPLE + "Edit" + ChatColor.YELLOW + "     '/arena edit <name>' to edit a select arena");

				return false;
			}
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("create")) {
					if (args.length > 1) {
						ArenaConfig.CreateArena(args[1], player.getName());
					} else
						player.sendMessage(ChatColor.RED + "Try /arena create <ArenaName>");
				}

				if (args[0].equalsIgnoreCase("delete")) {
					if (args.length > 1) {
						// ArenaConfig.DeleteArena(args[1], player);
						player.sendMessage(ChatColor.RED + "Try /arena delete <ArenaName> <--- currently not working, edit arenas.yml manually");
					} else
						player.sendMessage(ChatColor.RED + "Try /arena delete <ArenaName> <--- currently not working, edit arenas.yml manually");
				}

				if (args[0].equalsIgnoreCase("setpoint")) {
					if (args.length > 1) {
						ArenaConfig.SetArenaSpawnPoint(args[1], player);
					} else
						player.sendMessage(ChatColor.RED + "Try /arena setpoint <ArenaName>");
				}

			} else
				player.sendMessage(ChatColor.RED + "Try /arena help");
		}
		return true;
	}
}
