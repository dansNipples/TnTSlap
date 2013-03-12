package com.Nips.TnTSlap.Listeners.Entities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.Nips.TnTSlap.GameData;
import com.Nips.TnTSlap.PlayerManager;

public class EntityDamageListener implements Listener {
	PlayerManager pm = new PlayerManager();
	GameData game = new GameData();

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
	public void PlayerHungered(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
}