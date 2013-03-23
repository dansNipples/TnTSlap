package com.Nips.TnTSlap.Utils;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.Nips.TnTSlap.Config.SettingsConfig;

public class KillStreaks {

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
			user.getInventory().remove(im);
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
		user.getInventory().remove(im);
	}

	public static void CheckForKillstreak(Player p, int kills) {
		if (getKillstreak(kills).getType() == Material.APPLE) {
			return;
		} else {
			rewardKillStreak(p, kills);
		}
	}

	/*********************************** TNT ********************************************/
	public static void explosionsEveryWhere(World world, Player attacker) {
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

	/*********************************** Electro Rod ********************************************/
	public static void electroBoom(World world, Player attacker) {

		float HKnockback = 15.0f;
		float VKnockback = 2.0f;

		Block targetBlock = attacker.getTargetBlock(null, 20);
		if (targetBlock == null) {
			attacker.getInventory().addItem(getKillstreak(SettingsConfig.getSettingsConfig().getInt("KillStreaks.ElectroRod")));
			return;
		}
		Location loc = targetBlock.getLocation();
		final Collection<Entity> entities = targetBlock.getLocation().getWorld().getEntities();
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
	public static void witherBowShoot(World world, Player attacker) {

	}
}
