package com.Nips.TnTSlap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KillStreaks {

	public static ItemStack getKillstreak(int killamount) {
		ItemStack item = new ItemStack(Material.AIR);
		switch (killamount) {
		case 2:
			item.setType(Material.TNT);
			break;
		case 10: // more killstreaks here
			break;
		}
		return item;
	}

	public static void rewardKillStreak(Player p, int kills) {
		p.getInventory().addItem(getKillstreak(kills));
	}
}
