package com.Nips.TnTSlap.Listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.Nips.TnTSlap.GameData;
import com.Nips.TnTSlap.PlayerManager;
import com.Nips.TnTSlap.TnTSlap;

public class EntityListener implements Listener {
	private TnTSlap plugin;

	public EntityListener(TnTSlap plugin) {
		this.plugin = plugin;
	}

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

	@EventHandler
	public void PlayerJoined(PlayerJoinEvent event) {
		PlayerManager.addPlayerToGame(event.getPlayer());
	}

	@EventHandler
	public void EntityAttackEntity(EntityDamageByEntityEvent event) {
		if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
			if (GameData.Pvp == false) {
				event.setCancelled(true);
				return;
			}
			Player target = (Player) event.getEntity();
			Player attacker = (Player) event.getDamager();
			target.getWorld().playEffect(target.getLocation(), Effect.getById(2001), 55);
			event.setDamage(0);
			PlayerManager.ChangeLastAtked(target, attacker);
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

	@EventHandler
	public void itemRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getItem().getType() == Material.TNT) {
				explosionsEveryWhere(event.getPlayer().getWorld(), event.getPlayer());
				event.getPlayer().getInventory().remove(event.getItem());
			}
		}
		Player player = event.getPlayer();
		if (event.getClickedBlock() == null) {

		} else if (event.getClickedBlock().getType() != null) {
			Block block = event.getClickedBlock();
			if (block.getTypeId() == 68 || block.getTypeId() == 63) {
				int x = Math.round(block.getLocation().getBlockX());
				int y = Math.round(block.getLocation().getBlockY());
				int z = Math.round(block.getLocation().getBlockZ());
				Sign sign = (Sign) block.getState();
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if (sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nJoin Game")) {
						player.sendMessage(ChatColor.YELLOW + "Joined Game");
						PlayerManager.addPlayerToGame(player);
						// SpawnFunction.getSpawnPoints(sign.getLine(2)); // check if game is started or not, otherwise runs through gamestart
					}
					if (sign.getLine(0).equals("§a[TNTSLAP]") && sign.getLine(1).equals("§nLeave Game")) {
						player.sendMessage(ChatColor.YELLOW + "Left Game(theoretically)");
						// gotta setup this bugger
					}
				}

			}
			return;
		}
	}

	public void explosionsEveryWhere(World world, Player attacker) {
		attacker.getWorld().createExplosion(attacker.getLocation().getX(), attacker.getLocation().getY() + 1, attacker.getLocation().getZ(), 0.0F, false, false);

		float knockback = 15.0f;

		List<Entity> entities = attacker.getNearbyEntities(3.0, 3.0, 3.0);
		if (entities.contains(attacker)) {
			entities.remove(attacker);
		}

		for (Entity e : entities) {
			e.getWorld().createExplosion(e.getLocation().getX(), e.getLocation().getY() + 1, e.getLocation().getZ(), 0.0F, false, false);
			Vector v = e.getVelocity().add(e.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockback));
			v.setY(2.0f);
			e.setVelocity(v);
			PlayerManager.ChangeLastAtked((Player) e, attacker);
		}
	}
}
