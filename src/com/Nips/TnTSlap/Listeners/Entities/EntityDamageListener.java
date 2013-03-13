package com.Nips.TnTSlap.Listeners.Entities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Nips.TnTSlap.GameData;
import com.Nips.TnTSlap.PlayerManager;

public class EntityDamageListener implements Listener {
	PlayerManager pm = new PlayerManager();
	GameData game = new GameData();

	@EventHandler
	public void PlayerJoined(PlayerJoinEvent event) {
		pm.addPlayerToGame(event.getPlayer());
	}

	@EventHandler
	public void EntityDamaged(EntityDamageEvent event) {

	}

	@EventHandler
	public void EntityDamageEntity(EntityDamageByEntityEvent event) {
		// && game.PlayersInGame.contains((Player) target)
		if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
			Player target = (Player) event.getEntity();
			Player attacker = (Player) event.getDamager();
			pm.ChangeLastAtked(target, attacker);

		}
	}

	@EventHandler
	public void HungerEvent(FoodLevelChangeEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player p = (Player) event.getEntity();
			p.setSaturation(20.0f);
			event.setCancelled(true);
		}
	}
}