package com.Nips.TnTSlap.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getLocation().getY() < -5) {
			player.teleport(player.getWorld().getSpawnLocation());
			player.setNoDamageTicks(25);
		}
	}
}
