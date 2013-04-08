package com.Nips.TnTSlap.Utils.Combat;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.PlayerManager;

public class CombatHandler {

	public static void playerHit(Player attacker, Player defender, ItemStack attackingItem) {
		if (!GameData.getGameState()) {
			return;
		}
		int x = 1 + (int) (Math.random() * ((4 - 1) + 1));

		switch (attackingItem.getTypeId()) {
		case 349: // tis a fish
			knockPlayerBack(attacker, defender);
			ExpHandler.addLevel(defender, x);
			break;
		case 266: // tis gold
			knockPlayerBack(attacker, defender);
			ExpHandler.addLevel(defender, x);
			break;
		default:
			break;

		}
	}

	public static void knockPlayerBack(Player attacker, Player defender) {
		if (attacker.getItemInHand().getType() == Material.RAW_FISH) {
			defender.getWorld().playEffect(defender.getLocation(), Effect.getById(2001), 55);
		} else if (attacker.getItemInHand().getType() == Material.GOLD_INGOT) {
			defender.getWorld().playEffect(defender.getLocation(), Effect.getById(2001), 41);
		}
		// - 0.00058 *(x- 100)² + 6 == x
		// - 0.00028 *(x- 100)² + 3 == y
		float knockback = (float) ((float) -0.00058 * Math.pow(defender.getLevel() - 100, 2) + 6);
		float Yknockback = (float) ((float) -0.00028 * Math.pow(defender.getLevel() - 100, 2) + 3);
		if (defender.getLevel() == 0) {
			knockback = 0.2f;
			Yknockback = 0.2f;
		} else if (defender.getLevel() > 100 || knockback > 8.0 || Yknockback > 3.0) {
			knockback = 8;
			Yknockback = 3;
		}
		Vector v = defender.getVelocity().add(defender.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockback));
		v.setY(Yknockback);
		defender.setVelocity(v);

		PlayerManager.ChangeLastAtked(defender, attacker);
	}

	public static Vector getKnockBack(Player p, Player attacker) {
		float knockback = (float) Math.pow(ExpHandler.getLevel(p), 2) / 1000;
		if (knockback < 1) {
			knockback = 1;
		} else if (knockback > 100) {
			knockback = 100;
		}
		Vector v = p.getVelocity().add(p.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockback));
		return v;
	}
}
