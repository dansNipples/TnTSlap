package com.Nips.TnTSlap.Utils.Combat;

import org.bukkit.entity.Player;

public class ExpHandler {

	public static void addLevel(Player p, int level) {
		p.setLevel(p.getLevel() + level);
	}

	public static void resetLevel(Player p) {
		p.setLevel(0);
	}

	public static int getLevel(Player p) {
		return p.getLevel();
	}
}
