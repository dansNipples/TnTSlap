package com.Nips.TnTSlap.Listeners.Entities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.Nips.TnTSlap.GameData;
import com.Nips.TnTSlap.PlayerManager;

public class PlayerMoveListener implements Listener {
	PlayerManager pm = new PlayerManager();
	GameData game = new GameData();

	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getLocation().getY() < -5) {
			pm.PlayerFell(player);
		}
	}
}
