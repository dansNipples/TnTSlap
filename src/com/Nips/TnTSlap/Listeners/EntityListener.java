package com.Nips.TnTSlap.Listeners;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.KillStreaks;
import com.Nips.TnTSlap.Utils.PlayerManager;

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
				/** Quick Checks Start **/
				if (GameData.Pvp == false) { // check if pvp is off
					event.setCancelled(true);
					return;
				}
				if (!(p2.getItemInHand().getTypeId() == 349)) {
					event.setCancelled(true);
					return;
				}
				Location loc = event.getEntity().getLocation(); // check if player is falling
				Location loc2 = event.getDamager().getLocation();
				loc2.setY(loc2.getY() - 2);
				loc.setY(loc.getY() - 1);
				int block = loc.getWorld().getBlockTypeIdAt(loc);
				int block2 = loc.getWorld().getBlockTypeIdAt(loc2);
				if (block == 0 && block2 == 0) {
					event.setCancelled(true);
					return;
				}
				/** Quick Checks End **/

				Player target = (Player) event.getEntity();
				Player attacker = (Player) event.getDamager();
				target.getWorld().playEffect(target.getLocation(), Effect.getById(2001), 55);
				float Hknockback = (float) SettingsConfig.getSettingsConfig().getDouble("Default_Horizontal_KnockBack");
				float Yknockback = (float) SettingsConfig.getSettingsConfig().getDouble("Default_Verticle_KnockBack");
				Vector v = target.getVelocity().add(target.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(Hknockback));
				v.setY(Yknockback);
				target.setVelocity(v);
				event.setDamage(0);
				PlayerManager.ChangeLastAtked(target, attacker);
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
			if (GameData.PlayersInGame.contains(p)) { // no fall damage for players

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
		Player player = event.getPlayer();
		if (event.getClickedBlock() == null) {

		} else if (event.getClickedBlock().getType() != null) {
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

	// @EventHandler
	// public void onShoot(ProjectileLaunchEvent event) {
	// if (event.getEntity() instanceof Arrow) {
	// Arrow arrow = (Arrow) event.getEntity();
	// if (arrow.getShooter() instanceof Player) {
	// Player shooter = (Player) arrow.getShooter();
	// if (shooter.getItemInHand().getType() == Material.BOW && GameData.PlayersInGame.contains(shooter)) {
	// event.setCancelled(true);
	// shooter.launchProjectile(WitherSkull.class).setVelocity(arrow.getVelocity());
	//
	// }
	// }
	// }
	// }
	/** ONE DAY... **/
	// @EventHandler
	// public void onProjectileHit(ProjectileHitEvent event) {
	// Bukkit.getServer().broadcastMessage("Leroy by " + event.getEntity().getShooter() + " & " + event.getEntityType());
	//
	// }
	//
	// @EventHandler
	// public void EntityShoot(EntityShootBowEvent event) {
	//
	// }

	// ********************************** Player Joins or Leaves ********************************************//

	@EventHandler
	public void playerJoin(PlayerLoginEvent event) {

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

}
