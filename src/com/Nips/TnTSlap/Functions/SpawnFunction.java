package com.Nips.TnTSlap.Functions;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.ArenaConfig;

public class SpawnFunction {
	public static void SpawnPlayer(Player p) {
		String point = grabRandomPoint("dansnipple");
		if (point == null) {
			// repick map
			Bukkit.getServer().broadcastMessage("uh oh..  Messed up finding map points!");
			return;
		}
		String[] s = point.split(":");
		Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		loc.setYaw(Float.parseFloat(s[3]));
		loc.setPitch(Float.parseFloat(s[4]));
		p.teleport(loc);
	}

	public static String grabRandomPoint(String MapName) {
		if (!ArenaConfig.getArenaConfig().contains(MapName)) {
			return null;
		}

		Set<String> points = ArenaConfig.getArenaConfig().getConfigurationSection(MapName).getKeys(false);
		Random rand = new Random();
		Integer i = rand.nextInt(points.size() + 1);
		if (i == 0) {
			i = 1 + rand.nextInt(points.size());
		}
		String s = ArenaConfig.getArenaConfig().getString(MapName + "." + i);
		return s;
	}

	public static String grabRandomMap() {
		return "1";
	}
}
