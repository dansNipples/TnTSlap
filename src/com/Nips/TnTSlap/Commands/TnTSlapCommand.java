package com.Nips.TnTSlap.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.GameManager;
import com.Nips.TnTSlap.Utils.PlayerManager;

public class TnTSlapCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("join")) {
				PlayerManager.addPlayerToGame(player);
			}
			if (args[0].equalsIgnoreCase("leave")) {
				PlayerManager.removePlayerFromGame(player);
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
				player.sendMessage(ChatColor.AQUA + "   Game In Session: " + GameData.Started);
				player.sendMessage(ChatColor.AQUA + "   Players In Game: " + GameData.PlayersInGame.size() + "/" + SettingsConfig.getSettingsConfig().getInt("Max_Players"));
			}
		} else
			return false;

		return true;
	}
}
