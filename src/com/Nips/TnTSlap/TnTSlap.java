package com.Nips.TnTSlap;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Listeners.PlayerMoveListener;

public class TnTSlap extends JavaPlugin {

	TnTSlapCommand SlapCommand = new TnTSlapCommand(this);

	@Override
	public void onEnable() {
		getLogger().info("Enabled");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerMoveListener(), this);
		this.getCommand("tntslap").setExecutor(SlapCommand);
	}

	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}
}
