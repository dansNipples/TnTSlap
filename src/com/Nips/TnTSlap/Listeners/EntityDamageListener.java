package com.Nips.TnTSlap.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.Nips.TnTSlap.GameData;

public class EntityDamageListener implements Listener {
	private GameData game = new GameData();

	@EventHandler
	public void EntityDamaged(EntityDamageEvent event) {

	}

	@EventHandler
	public void EntityDamageEntity(EntityDamageByEntityEvent event) {
		Entity target = event.getEntity();
		Entity attacker = event.getDamager();
		if (target.getType() == EntityType.PLAYER && attacker.getType() == EntityType.PLAYER && game.PlayersInGame.contains((Player) target)) {

		}
	}
}
