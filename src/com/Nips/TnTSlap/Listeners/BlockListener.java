package com.Nips.TnTSlap.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Stats.Stats;
import com.Nips.TnTSlap.Utils.GameData;

public class BlockListener implements Listener {

	// ********************************** Block Break ********************************************//

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (GameData.isPlaying(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
	}

	// ********************************** Sign Changed ********************************************//

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).equalsIgnoreCase("tntjoin") && player.hasPermission("tntslap.set.signs")) {
			event.setLine(0, "§a[TNTSLAP]");
			event.setLine(1, "§nJoin Game");
			event.setLine(2, ChatColor.UNDERLINE + "" + GameData.getPlayersIngame().size() + "/" + SettingsConfig.getSettingsConfig().getInt("Max_Players"));
		}
		if (event.getLine(0).equalsIgnoreCase("tnttops") && player.hasPermission("tntslap.set.signs")) {
			event.setLine(0, "§a[Top 3]");
			event.setLine(1, "#1 " + Stats.getPlayerAtRank(1));
			event.setLine(2, "#2 " + Stats.getPlayerAtRank(2));
			event.setLine(3, "#3 " + Stats.getPlayerAtRank(3));
		}
		if (event.getLine(0).equalsIgnoreCase("tntstats") && player.hasPermission("tntslap.set.signs")) {
			event.setLine(0, "§a[TNT STATS]");

		}
	}

	public static void updateJoinSign(Sign s) {
		s.setLine(0, "§a[TNTSLAP]");
		s.setLine(1, "§nJoin Game");
		s.setLine(2, ChatColor.UNDERLINE + "" + GameData.getPlayersIngame().size() + "/" + SettingsConfig.getSettingsConfig().getInt("Max_Players"));
		s.update();
	}

	// ********************************** Block Placed ********************************************//

	@EventHandler
	public void BlockPlaced(BlockPlaceEvent event) {
		if (GameData.isPlaying(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
}
