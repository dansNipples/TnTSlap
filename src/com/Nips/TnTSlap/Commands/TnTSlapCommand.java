package com.Nips.TnTSlap.Commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.Config.ArenaConfig;
import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.GameManager;
import com.Nips.TnTSlap.Utils.PlayerManager;

public class TnTSlapCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0 || args.length > 1 && args[0].equalsIgnoreCase("help")) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "  ======  " + ChatColor.YELLOW + "" + ChatColor.BOLD + "TntSlap Commands" + ChatColor.LIGHT_PURPLE + "  ======  ");
			player.sendMessage(ChatColor.AQUA + "    Aliases:" + ChatColor.YELLOW + " [tntslap: ts]");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Join:" + ChatColor.YELLOW + " '/tntslap join' to join the game!");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Leave:" + ChatColor.YELLOW + " '/tntslap leave' to leave the game!");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Helpme:" + ChatColor.YELLOW + " '/tntslap helpme' to get a book for help!");
			if (player.isOp() == true || player.hasPermission("tntslap.set.nextmap")) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Start:" + ChatColor.YELLOW + " '/tntslap start' to force start a game! ");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Set map:" + ChatColor.YELLOW + " '/tntslap set map' to force next map! [NOT WORKING]");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "ListMaps:" + ChatColor.YELLOW + " '/tntslap listmaps' to get all map names Case sensitive");
			}
			return true;
		}

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("join")) {
				PlayerManager.addPlayerToGame(player);
			}
			if (args[0].equalsIgnoreCase("leave")) {
				PlayerManager.removePlayerFromGame(player);
			}
			if (args[0].equalsIgnoreCase("helpme")) {
				if (GameData.isPlaying(player) == false) {
					if (!player.getInventory().contains(Material.WRITTEN_BOOK)) {
						player.getInventory().addItem(HelpBookBuilder.BuildBook());
					}
				} else
					GameManager.messageTntPlayer(player, ChatColor.RED + "cannot use this ingame!");
			}
			if (args[0].equalsIgnoreCase("listmaps")) {
				Set<String> keys = ArenaConfig.getArenaConfig().getKeys(false);
				String stack = "";
				for (String s : keys) {
					stack = stack + ChatColor.AQUA + s + ChatColor.RED + ", ";
				}
				player.sendMessage(stack);
			}
			if (args[0].equalsIgnoreCase("start")) {
				if (player.isOp()) {
					GameManager.resetGame();
				}
			}
			if (args[0].equalsIgnoreCase("set")) {
				if (args.length > 1) {
					if (player.hasPermission("tntslap.set.nextmap")) {
						if (args[1].equalsIgnoreCase("nextmap")) {
							if (args.length > 2) {
								GameManager.setMap(args[2], player);
							} else
								player.sendMessage(ChatColor.RED + "Try /ts set nextmap <ArenaName>");
						}
					}
				} else
					player.sendMessage(ChatColor.RED + "Try /ts set [hittingaparatus, nextmap]");
			}
			if (args[0].equalsIgnoreCase("check")) {
				player.sendMessage(ChatColor.AQUA + "   Current Map: " + GameData.getCurrentMap());
				player.sendMessage(ChatColor.AQUA + "   Game In Session: " + GameData.getGameState());
				player.sendMessage(ChatColor.AQUA + "   Players In Game: " + GameData.getPlayersIngame().size() + "/" + SettingsConfig.getSettingsConfig().getInt("Max_Players"));
			}
		} else
			return false;

		return true;
	}
}
