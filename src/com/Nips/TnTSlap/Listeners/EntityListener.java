package com.Nips.TnTSlap.Listeners;

import net.minecraft.server.v1_5_R2.EntityLiving;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Nips.TnTSlap.KillStreaks.KillStreaks;
import com.Nips.TnTSlap.Stats.Stats;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.GameManager;
import com.Nips.TnTSlap.Utils.PlayerManager;
import com.Nips.TnTSlap.Utils.Combat.CombatHandler;

public class EntityListener implements Listener {

	// ********************************** Player Moved ********************************************//

	@EventHandler
	public void PlayerMoved(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		// if (p.getInventory().getBoots() == null) {
		// return;
		// }
		if (GameData.isPlaying(p)) {
			Block b = p.getWorld().getBlockAt(p.getLocation().subtract(0, 1, 0));

			if (KillStreaks.canPlayerDoubleJump.containsKey(p.getName()) && KillStreaks.canPlayerDoubleJump.get(p.getName()) == false && b.getType() != Material.AIR && p.getVelocity().getY() <= 0) {
				KillStreaks.canPlayerDoubleJump.put(p.getName(), true);
			}
			// if (event.getPlayer().getLocation().getY() <= -5) {
			// if (p != null) {
			// PlayerManager.PlayerFell(p);
			// p.setFallDistance(0f);
			// }
			// }
		}
	}

	// ********************************** Entity Attacked by Entity ********************************************//

	@EventHandler
	public void EntityAttackEntity(EntityDamageByEntityEvent event) {

		if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
			Player p = (Player) event.getEntity();
			Player p2 = (Player) event.getDamager();
			if (GameData.isPlaying(p) && GameData.isPlaying(p2)) {
				CraftEntity cEntity = (CraftEntity) p;
				EntityLiving entity = (EntityLiving) cEntity.getHandle();
				if (GameData.isPvp() == false || GameData.getGameState() == false) {
					event.setCancelled(true);
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
			} else
				event.setCancelled(true);

		}
	}

	// ********************************** Entity Damaged ********************************************//

	@EventHandler
	public void EntityDamaged(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			Player p = (Player) event.getEntity();
			if (GameData.isPlaying(p)) { // no damage generally
				if (event.getCause() == DamageCause.VOID) {
					PlayerManager.PlayerFell(p);
					p.setFallDistance(0f);
				}
				event.setDamage(0);

			}

		}
	}

	// ********************************** Player Interact ********************************************//

	@EventHandler
	public void itemRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (GameData.isPlaying(event.getPlayer())) {
				if (event.getPlayer().getItemInHand().getType() != Material.AIR) {
					if (event.getItem().getType() == Material.GOLD_INGOT) {
						KillStreaks.tryDoubleJump(event.getPlayer());
						return;
					} else if (event.getItem().getType() == Material.RAW_FISH) {
						return;
					}
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
						BlockListener.updateJoinSign(sign);
					} else if (sign.getLine(0).equals("§a[TNT STATS]")) {
						sign.setLine(1, event.getPlayer().getDisplayName());
						sign.setLine(2, "Wins: " + Stats.getPlayerWins(player));
						sign.setLine(3, "Rank: " + Stats.getPlayerRank(player) + "/" + Stats.getRankList().size());
						sign.update();
						Stats.organizeRanks();
					} else if (sign.getLine(0).equals("§a[Top 3]")) {
						sign.setLine(1, "#1 " + Stats.getPlayerAtRank(1));
						sign.setLine(2, "#2 " + Stats.getPlayerAtRank(2));
						sign.setLine(3, "#3 " + Stats.getPlayerAtRank(3));
						sign.update();
					}

				}

			}
			return;
		}
	}

	// ********************************** Throw item ********************************************//
	@EventHandler
	public void dropItem(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		if (GameData.isPlaying(p)) {
			if (event.getItemDrop().getItemStack().getType() == Material.WRITTEN_BOOK || event.getItemDrop().getItemStack().getType() == Material.GOLD_BOOTS || event.getItemDrop().getItemStack().getType() == Material.CHAINMAIL_BOOTS) {
				event.setCancelled(true);
			}
		} else
			event.setCancelled(true);
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
	// KillStreaks.witherBowShoot(shooter.getWorld(), shooter, arrow);
	// }
	// }
	// }
	// }
	//
	// /** ONE DAY... **/
	// @EventHandler
	// public void onProjectileHit(ProjectileHitEvent event) {
	// Player p = (Player) event.getEntity().getShooter();
	// p.sendMessage("" + event.getEntity().getEntityId());
	// KillStreaks.witherBowHit(event.getEntity());
	//
	// }

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
		if (GameData.isPlaying(event.getPlayer())) {
			PlayerManager.removePlayerFromGame(event.getPlayer());
		}

	}

	// ********************************** Command Send ********************************************//
	@EventHandler
	public void sendCommanddd(PlayerCommandPreprocessEvent event) {
		if (GameData.isPlaying(event.getPlayer())) {
			if (event.getPlayer().hasPermission("tntslap.bypass.nocommands")) {
				return;
			}
			if (!(event.getMessage().toLowerCase().startsWith("/tntslap") || event.getMessage().toLowerCase().startsWith("/ts"))) {
				GameManager.messageTntPlayer(event.getPlayer(), ChatColor.RED + "Cannot use that command ingame!");
				event.setCancelled(true);
			}
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
