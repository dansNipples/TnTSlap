package com.Nips.TnTSlap.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		Player player = event.getPlayer();
		int x = Math.round(block.getLocation().getBlockX());
		int y = Math.round(block.getLocation().getBlockY());
		int z = Math.round(block.getLocation().getBlockZ());
		if(block.getTypeId() == 68 || block.getTypeId() == 63){
			Sign sign = (Sign)block.getState();
			if(sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nJoin Game")){
				player.sendMessage(ChatColor.RED + "Join Game Sign Unregistered!");
			}
			if(sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nLeave Game")){
				player.sendMessage(ChatColor.RED + "Leave Game Sign Unregistered!");
			}
		}
	}
}
