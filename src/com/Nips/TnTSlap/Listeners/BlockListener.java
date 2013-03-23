package com.Nips.TnTSlap.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Utils.GameData;

public class BlockListener implements Listener {

	// ********************************** Block Break ********************************************//

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (GameData.PlayersInGame.contains(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
	}

	// ********************************** Sign Changed ********************************************//

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).equalsIgnoreCase("tntjoin") && player.hasPermission("tntslap.set.signs")) {
			event.setLine(0, "�a[TNTSLAP]");
			event.setLine(1, "�nJoin Game");
		}
		if (event.getLine(0).equalsIgnoreCase("tntjoin") && player.hasPermission("tntslap.set.signs")) {
			event.setLine(0, "�a[TNTSLAP]");
			event.setLine(1, ChatColor.UNDERLINE + "" + GameData.PlayersInGame.size() + "/" + SettingsConfig.getSettingsConfig().getInt("Max_Players"));
		}
	}

	// ********************************** Block Placed ********************************************//

	@EventHandler
	public void BlockPlaced(BlockPlaceEvent event) {
		if (GameData.PlayersInGame.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
}
