package com.Nips.TnTSlap.Listeners.Entities;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Nips.TnTSlap.GameData;
import com.Nips.TnTSlap.Functions.SpawnFunction;

public class PlayerInteractListener implements Listener {
	GameData game;
	SpawnFunction spawn;
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getClickedBlock() == null) {

		} else 
			if (event.getClickedBlock().getType() != null) {
			Block block = event.getClickedBlock();
			if (block.getTypeId() == 68 || block.getTypeId() == 63) {
				int x = Math.round(block.getLocation().getBlockX());
				int y = Math.round(block.getLocation().getBlockY());
				int z = Math.round(block.getLocation().getBlockZ());
				Sign sign = (Sign) block.getState();
				if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					if (sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nJoin Game")) {
						player.sendMessage(ChatColor.YELLOW + "Joined Game(theoretically)");
						game.PlayersInGame.add(player);
						spawn.getSpawnPoints(sign.getLine(2));
					}
					if (sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nLeave Game")) {
						player.sendMessage(ChatColor.YELLOW + "Left Game(theoretically)");
						game.PlayersInGame.remove(player);
					}
				}
			}
		} else
			return;

		/*
		 * for(int i = 0; i < amount; i++){ control++; int px = plugin.getSignConfig().getInt("Signs." + (control + 1) + ".x"); int py = plugin.getSignConfig().getInt("Signs." + (control + 1) + ".y"); int pz = plugin.getSignConfig().getInt("Signs." + (control + 1) + ".z"); if(x == px){ if(y == py){ if(z == pz){ sign.setLine(3, control); } } } }
		 */
	}
}
