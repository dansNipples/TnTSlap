package com.Nips.TnTSlap.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.Config.ArenaConfig;
import com.Nips.TnTSlap.Utils.GameManager;

public class SetLobbyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		if (player.isOp() == true) {
			if (args.length == 0 || args.length > 1 && args[0].equalsIgnoreCase("help")) {
				if (!player.hasPermission("tntslap.set.createmap") && !player.hasPermission("tntslap.set.spawnpoints")) {
					GameManager.messageTntPlayer(player, ChatColor.RED + "No Perms to do this!");
				} else {
					player.sendMessage(ChatColor.LIGHT_PURPLE + "  ======  " + ChatColor.YELLOW + "" + ChatColor.BOLD + "Arena Commands" + ChatColor.LIGHT_PURPLE + "  ======  ");
					if (player.hasPermission("tntslap.set.createmap")) {
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Create:" + ChatColor.YELLOW + " '/arena create <ArenaName>' to create a new arena");
					}
					// player.sendMessage(ChatColor.LIGHT_PURPLE + "Edit:" + ChatColor.YELLOW + " '/arena edit <ArenaName>' to edit a select arena");
					if (player.hasPermission("tntslap.set.spawnpoints")) {
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Setpoint:" + ChatColor.YELLOW + " '/arena setpoint <ArenaName>' to set a spawn point for the arena");
					}
				}
				return true;
			}
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("create")) {
					if (args.length > 1) {
						if (player.hasPermission("tntslap.set.createmap")) {
							ArenaConfig.CreateArena(args[1], player.getName());
						} else
							GameManager.messageTntPlayer(player, ChatColor.RED + "No Perms to do this!");
					} else
						GameManager.messageTntPlayer(player, ChatColor.RED + "Try /arena create <ArenaName>");
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
						if (player.hasPermission("tntslap.set.spawnpoints")) {
							ArenaConfig.SetArenaSpawnPoint(args[1], player);
						} else
							GameManager.messageTntPlayer(player, ChatColor.RED + "No Perms to do this!");
					} else
						GameManager.messageTntPlayer(player, ChatColor.RED + "Try /arena setpoint <ArenaName>");
				}

			} else
				player.sendMessage(ChatColor.RED + "Try /arena help");
		}
		return true;
	}
}
