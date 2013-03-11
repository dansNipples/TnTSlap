package com.Nips.TnTSlap;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TnTSlap extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info("Enabled");
		PluginManager pm = getServer().getPluginManager();
	}

	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}
}
