package com.Nips.TnTSlap;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TnTSlap extends JavaPlugin {
	/* ///// ArrayLists & HashMaps ///// */
	ArrayList<Player> PlayersInGame = new ArrayList<Player>();

	/* ///// Booleans ///// */
	public boolean gameInSession;

	/* ///// Integers and Strings ///// */

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
