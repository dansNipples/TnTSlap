package com.Nips.TnTSlap;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Listeners.Block.BlockBreakListener;
import com.Nips.TnTSlap.Listeners.Block.SignChangeListener;
import com.Nips.TnTSlap.Listeners.Entities.EntityDamageListener;
import com.Nips.TnTSlap.Listeners.Entities.PlayerMoveListener;

public class TnTSlap extends JavaPlugin {
	/** Command Classes Implementation **/
	TnTSlapCommand SlapCommand = new TnTSlapCommand(this);

	/** Configuration Classes Implementation **/
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();

		/** Event Registration **/
		pm.registerEvents(new PlayerMoveListener(), this);
		pm.registerEvents(new BlockBreakListener(), this);
		pm.registerEvents(new SignChangeListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		/** Command Registration **/
		this.getCommand("tntslap").setExecutor(SlapCommand);

		getLogger().info("Enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}
}
