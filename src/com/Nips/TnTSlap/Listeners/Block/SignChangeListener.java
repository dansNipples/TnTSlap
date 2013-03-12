package com.Nips.TnTSlap.Listeners.Block;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener{
	@EventHandler
	public void onSignChange(SignChangeEvent event){
		Player player = event.getPlayer();
		if(event.getLine(0).equalsIgnoreCase("tntjoin")){
			event.setLine(0, "§a[TNTSLAP]");
			event.setLine(1, "§nJoin Game");
			event.setLine(2, "ArenaName");
			event.setLine(3, "Players 0/20");
			player.sendMessage(ChatColor.GREEN + "Join Sign Registered!");
		}
		if(event.getLine(0).equalsIgnoreCase("tntleave")){
			event.setLine(0, "§a[TNTSLAP]");
			event.setLine(1, "§nLeave Game");
			player.sendMessage(ChatColor.GREEN + "Leave Sign Registered!");
		}
	}
}
