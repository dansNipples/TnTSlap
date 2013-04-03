package com.Nips.TnTSlap.KillStreaks;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.Nips.TnTSlap.TnTSlap;
import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.GameManager;
import com.Nips.TnTSlap.Utils.PlayerManager;

public class KillStreaks {
	private static TnTSlap plugin;

	public KillStreaks(TnTSlap plugin) {
		KillStreaks.plugin = plugin;
	}

	public static Map<Integer, Player> tempwither = new HashMap<Integer, Player>();
	public static Map<Player, Boolean> canPlayerDoubleJump = new HashMap<Player, Boolean>();

	public static ItemStack getKillstreak(int killamount) {
		ItemStack item = new ItemStack(Material.APPLE);
		ItemMeta is = item.getItemMeta();
		if (killamount == SettingsConfig.getSettingsConfig().getInt("KillStreaks.Tnt")) {
			item = new ItemStack(Material.TNT);
			is.setDisplayName("§4B00M! ;)");
		}
		if (killamount == SettingsConfig.getSettingsConfig().getInt("KillStreaks.ElectroRod")) {
			item = new ItemStack(Material.BLAZE_ROD);
			is.setDisplayName("§3Electro §bRod");
		}
		if (killamount == SettingsConfig.getSettingsConfig().getInt("KillStreaks.WitherBow")) {
			item = new ItemStack(Material.BOW);
			is.setDisplayName("§8Wither Bow");
		}
		item.setItemMeta(is);
		return item;
	}

	public static void rewardKillStreak(Player p, int kills) {
		p.getInventory().addItem(getKillstreak(kills));
	}

	// ********************************** KillStreak Functions ********************************************//
	public static void activateKillStreak(ItemStack im, Player user) {
		if (GameData.Started == false) {
			// if (im.getType() == Material.GOLD_INGOT || im.getType() == Material.RAW_FISH) {
			// user.sendMessage(ChatColor.GRAY + "Uh oh.. You ate you're weapon!");
			// user.getInventory().remove(im);
			// return;
			// }
			user.getInventory().removeItem(im);
			return;
		}
		switch (im.getType()) {
		case TNT:
			explosionsEveryWhere(user.getWorld(), user);
			break;
		case BLAZE_ROD:
			electroBoom(user.getWorld(), user);
			break;
		default:
			break;
		}
		if(im.getAmount() > 1){
			int amount = im.getAmount();
			int sub = amount - 1;
			user.getInventory().removeItem(im);
			im.setAmount(sub);
			user.getInventory().addItem(im);
		} else
		user.getInventory().removeItem(im);
	}

	public static void CheckForKillstreak(Player p, int kills) {
		if (getKillstreak(kills).getType() == Material.APPLE) {
			return;
		} else {
			rewardKillStreak(p, kills);
			GameManager.messageTntPlayer(p, ChatColor.GREEN + "Killstreak reward earnt! Right click to Use!");
		}
	}

	/*********************************** TNT ********************************************/
	public static void explosionsEveryWhere(World world, Player attacker) {
		attacker.getWorld().createExplosion(attacker.getLocation().getX(), attacker.getLocation().getY() + 1, attacker.getLocation().getZ(), 0.0F, false, false);

		float knockback = 10.0f;

		List<Entity> entities = attacker.getNearbyEntities(3.0, 3.0, 3.0);
		if (entities.contains(attacker)) {
			entities.remove(attacker);
		}
		if (entities.isEmpty() == true) {
			GameManager.messageTntPlayer(attacker, ChatColor.YELLOW + "You Missed! Player must be within a 3 block radius for tnt to work!");
		}

		for (Entity e : entities) {
			if (e.getType() != EntityType.PLAYER) {
				return;
			}
			e.getWorld().createExplosion(e.getLocation().getX(), e.getLocation().getY() + 1, e.getLocation().getZ(), 0.0F, false, false);
			Vector v = e.getVelocity().add(e.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockback));
			v.setY(2.0f);
			e.setVelocity(v);
			PlayerManager.ChangeLastAtked((Player) e, attacker);
		}
	}

	/*********************************** Electro Rod ********************************************/
	public static void electroBoom(World world, Player attacker) {

		float HKnockback = 15.0f;
		float VKnockback = 2.0f;

		Block targetBlock = attacker.getTargetBlock(null, 100);

		if (targetBlock.getTypeId() == 0) {
			GameManager.messageTntPlayer(attacker, ChatColor.YELLOW + "You Missed! Aim for the block under the player next time!");
			return;
		}
		Location loc = targetBlock.getLocation();
		final Collection<Entity> entities = targetBlock.getLocation().getWorld().getEntities();

		if (entities.contains(attacker)) {
			entities.remove(attacker);
		}
		final int radX = 3;
		final int radZ = 3;
		final int radY = 2;
		world.strikeLightning(targetBlock.getLocation());

		Player closest = null;
		double curDistance;
		double minimalDistance = Math.pow(3, 2);
		for (Entity e : entities) {
			final double LocX = e.getLocation().getX();
			final double LocZ = e.getLocation().getZ();
			final double LocY = e.getLocation().getY();

			if (LocX <= (loc.getX() + radX) && LocX >= (loc.getX() - radX) && LocZ <= (loc.getZ() + radZ) && LocZ >= (loc.getZ() - radZ) && LocY <= (loc.getY() + radY) && LocY >= (loc.getY() - radY)) {
				curDistance = e.getLocation().distanceSquared(loc);
				if (curDistance < minimalDistance) {
					minimalDistance = curDistance;
					closest = (Player) e;
				}
			}

		}
		if (closest == null) {
			return;
		}
		Vector v = closest.getVelocity().add(closest.getLocation().toVector().subtract(targetBlock.getLocation().toVector()).normalize().multiply(HKnockback));
		v.setY(VKnockback);
		closest.setVelocity(v);
		PlayerManager.ChangeLastAtked(closest, attacker);
	}

	/*********************************** Wither Bow ********************************************/
	public static void witherBowShoot(World world, Player attacker, Entity arrow) {
		attacker.launchProjectile(WitherSkull.class).setVelocity(arrow.getVelocity());
		tempwither.put(arrow.getEntityId() + 1, attacker);
	}

	public static void witherBowHit(Projectile head, Entity Attackee) {
		Player p = (Player) head.getShooter();
		if (tempwither.containsKey(head.getEntityId())) {
			p.sendMessage(head.getEntityId() + "");
			Attackee.getVelocity().setY(2.0);
		}
	}

	/*********************************** Double Jump ********************************************/

	public static void tryDoubleJump(final Player p) {
		Vector v2 = p.getLocation().getDirection();
		v2.setY(1.32);
		if (canPlayerDoubleJump.containsKey(p) && canPlayerDoubleJump.get(p) == false) {
			return;
		}
		p.setVelocity(v2);
		canPlayerDoubleJump.put(p, false);
	}

}
