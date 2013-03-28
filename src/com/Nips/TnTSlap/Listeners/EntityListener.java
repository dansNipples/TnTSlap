package com.Nips.TnTSlap.Listeners;

import net.minecraft.server.v1_5_R1.EntityLiving;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_5_R1.entity.CraftEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.KillStreaks;
import com.Nips.TnTSlap.Utils.PlayerManager;
import com.Nips.TnTSlap.Utils.Combat.CombatHandler;

public class EntityListener implements Listener {

	// ********************************** Player Moved ********************************************//

	@EventHandler
	public void PlayerMoved(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (event.getPlayer().getLocation().getY() <= -5 && GameData.PlayersInGame.contains(event.getPlayer())) {
			if (p != null) {
				PlayerManager.PlayerFell(p);
				p.setFallDistance(0f);
			}
		}
	}

	// ********************************** Entity Attacked by Entity ********************************************//

	@EventHandler
	public void EntityAttackEntity(EntityDamageByEntityEvent event) {

		if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
			Player p = (Player) event.getEntity();
			Player p2 = (Player) event.getDamager();
			if (GameData.PlayersInGame.contains(p) && GameData.PlayersInGame.contains(p2)) {
				CraftEntity cEntity = (CraftEntity) p;
				EntityLiving entity = (EntityLiving) cEntity.getHandle();
				if (GameData.Pvp == false || GameData.Started == false) {
					return;
				}
				if (entity.maxNoDamageTicks < 20) {
					entity.maxNoDamageTicks = 20;

				}
				if (entity.noDamageTicks < entity.maxNoDamageTicks / 2.0F) {
					CombatHandler.playerHit(p2, p, p2.getItemInHand());
					entity.noDamageTicks = 20;

				}
				event.setCancelled(true);
			}
		}
	}

	// ********************************** Entity Damaged ********************************************//

	@EventHandler
	public void EntityDamaged(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			Player p = (Player) event.getEntity();
			if (GameData.PlayersInGame.contains(p)) { // no damage generally

				event.setDamage(0);

			}

		}
	}

	// ********************************** Player Interact ********************************************//

	@EventHandler
	public void itemRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (GameData.PlayersInGame.contains(event.getPlayer())) {
				if (event.getPlayer().getItemInHand().getType() != Material.AIR) {
					KillStreaks.activateKillStreak(event.getItem(), event.getPlayer());
				}
			}
		}
		if (event.getClickedBlock() == null) {
			return;
		}
		Player player = event.getPlayer();
		if (event.getClickedBlock().getType() != null) {
			Block block = event.getClickedBlock();
			if (block.getTypeId() == 68 || block.getTypeId() == 63) {
				Sign sign = (Sign) block.getState();
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if (sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nJoin Game")) {
						PlayerManager.addPlayerToGame(player);
					}
				}

			}
			return;
		}
	}

	// ********************************** Projectile Launched ********************************************//

	@EventHandler
	public void onShoot(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player shooter = (Player) arrow.getShooter();
				if (shooter.getItemInHand().getType() == Material.BOW && GameData.PlayersInGame.contains(shooter)) {
					event.setCancelled(true);
					KillStreaks.witherBowShoot(shooter.getWorld(), shooter, arrow);
				}
			}
		}
	}

	/** ONE DAY... **/
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Player p = (Player) event.getEntity().getShooter();
		p.sendMessage("" + event.getEntity().getEntityId());
		// KillStreaks.witherBowHit(event.getEntity());

	}

	// @EventHandler
	// public void EntityShoot(EntityShootBowEvent event) {
	//
	// }

	// ********************************** Player Joins or Leaves ********************************************//

	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent event) {
		if (GameData.PlayersInGame.contains(event.getPlayer())) {
			PlayerManager.removePlayerFromGame(event.getPlayer());
		}
	}

	// ********************************** Food Level Changed ********************************************//

	@EventHandler
	public void HungerEvent(FoodLevelChangeEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player p = (Player) event.getEntity();
			p.setSaturation(20.0f);
			event.setCancelled(true);
		}
	}

	// ********************************** Entity Explode ********************************************//

	@EventHandler
	public void entityExplode(EntityExplodeEvent event) {
		if (event.getEntityType() == EntityType.WITHER_SKULL && KillStreaks.tempwither.containsKey(event.getEntity().getEntityId())) {
			event.setCancelled(true);
		}
	}

}
