package com.Nips.TnTSlap.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.Nips.TnTSlap.PlayerManager;

public class PlayerMoveListener implements Listener {
	private final PlayerManager pm = new PlayerManager();

	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getLocation().getY() < -5) {

		}
	}
}
