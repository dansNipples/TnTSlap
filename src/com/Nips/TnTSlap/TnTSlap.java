package com.Nips.TnTSlap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.SetLobbyCommand;
import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Listeners.BlockListener;
import com.Nips.TnTSlap.Listeners.EntityListener;

public class TnTSlap extends JavaPlugin {
	/** Command Classes Implementation **/
	TnTSlapCommand SlapCommand = new TnTSlapCommand(this);
	SetLobbyCommand ArenaCommand = new SetLobbyCommand(this);
	/** Classes to register **/
	PlayerManager pm = new PlayerManager(this);
	GameManager gameManager = new GameManager(this);
	Timer time = new Timer(this);
	/** Listeners **/
	EntityListener entitylistener = new EntityListener(this);
	BlockListener blocklistener = new BlockListener(this);
	/** Config **/
	ArenaConfig arenaconfig = new ArenaConfig(this);

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		FileConfiguration config = ArenaConfig.getArenaConfig();
		/** Event Registration **/
		pm.registerEvents(entitylistener, this);
		pm.registerEvents(blocklistener, this);

		/** Command Registration **/
		this.getCommand("tntslap").setExecutor(SlapCommand);
		this.getCommand("arena").setExecutor(ArenaCommand);
		getLogger().info("Enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}

}
