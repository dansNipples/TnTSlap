package com.Nips.TnTSlap;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Listeners.BlockBreakListener;
import com.Nips.TnTSlap.Listeners.PlayerMoveListener;
import com.Nips.TnTSlap.Listeners.SignChangeListener;

public class TnTSlap extends JavaPlugin {
	/**Command Classes Implementation**/
		TnTSlapCommand SlapCommand = new TnTSlapCommand(this);
	/**Configuration Classes Implementation**/
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		
		/**Event Registration**/
			pm.registerEvents(new PlayerMoveListener(), this);
			pm.registerEvents(new BlockBreakListener(), this);
			pm.registerEvents(new SignChangeListener(), this);
		/**Command Registration**/
			this.getCommand("tntslap").setExecutor(SlapCommand);

		getLogger().info("Enabled");
	}
	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}
}
