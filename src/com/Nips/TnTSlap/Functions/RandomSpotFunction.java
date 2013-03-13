package com.Nips.TnTSlap.Functions;

import java.util.Random;

import org.bukkit.Location;

import com.Nips.TnTSlap.TnTSlap;

public class RandomSpotFunction {
	private TnTSlap plugin;
	public RandomSpotFunction(TnTSlap plugin){
		this.plugin = plugin;
	}
    public Location grabRandomSpot(Location loc, String string) {
        Random random = new Random();
        if (string.equalsIgnoreCase(plugin.getArenaConfig().getString("Arenas." + string))) {
                int rand = random.nextInt(plugin.getConfig().getInt("Arenas.MaxArenaPoints"));
                if (rand == 0) {
                        rand = plugin.getConfig().getInt("Arenas.MaxArenaPoints") - 1;
                }
                Float pitch = (float) plugin.getConfig().getDouble("Arenas." + string + rand + ".Pitch");
                Float yaw = (float) plugin.getConfig().getDouble("Arenas." + string + rand + ".Yaw");
                loc.setX(plugin.getArenaConfig().getDouble("Arenas." + string + rand + ".X"));
                loc.setY(plugin.getArenaConfig().getDouble("Arenas." + string + rand + ".Y"));
                loc.setZ(plugin.getArenaConfig().getDouble("Arenas." + string + rand + ".Z"));
                loc.setPitch(pitch);
                loc.setYaw(yaw);
        }
        return loc;
    }
}
