package com.Nips.TnTSlap.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.GameManager;
import com.Nips.TnTSlap.PlayerManager;
import com.Nips.TnTSlap.TnTSlap;

public class TnTSlapCommand implements CommandExecutor {
	private TnTSlap plugin;

	public TnTSlapCommand(TnTSlap plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("join")) {
				// playermanager.addPlayerToGame(player);
				PlayerManager.addPlayerToGame(player);
			} else if (args[0].equalsIgnoreCase("start")) {
				GameManager.startGame();
			}
		} else
			return false;

		return true;
	}
}
