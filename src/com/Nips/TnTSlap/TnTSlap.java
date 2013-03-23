package com.Nips.TnTSlap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.SetLobbyCommand;
import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Config.ArenaConfig;
import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Listeners.BlockListener;
import com.Nips.TnTSlap.Listeners.EntityListener;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.GameManager;
import com.Nips.TnTSlap.Utils.PlayerManager;

public class TnTSlap extends JavaPlugin {
	/** Command Classes Implementation **/
	TnTSlapCommand SlapCommand = new TnTSlapCommand();
	SetLobbyCommand ArenaCommand = new SetLobbyCommand();
	/** Classes to register **/
	PlayerManager pm = new PlayerManager();
	GameManager gameManager = new GameManager(this);
	public Timer time = new Timer(this);
	/** Listeners **/
	EntityListener entitylistener = new EntityListener();
	BlockListener blocklistener = new BlockListener();
	/** Config **/
	ArenaConfig arenaconfig = new ArenaConfig(this);
	SettingsConfig settingsconfig = new SettingsConfig(this);

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		FileConfiguration config = SettingsConfig.getSettingsConfig();
		/** Event Registration **/
		pm.registerEvents(entitylistener, this);
		pm.registerEvents(blocklistener, this);

		/** Command Registration **/
		this.getCommand("tntslap").setExecutor(SlapCommand);
		this.getCommand("arena").setExecutor(ArenaCommand);
		getLogger().info("Enabled");
		/** Config defaults **/
		config.addDefault("Kills_To_Win", 5);
		config.addDefault("Max_Players", 40);
		config.addDefault("Attack_Reset_Time", 5);
		config.addDefault("Default_Horizontal_KnockBack", 5.0);
		config.addDefault("Default_Verticle_KnockBack", 1.0);
		config.addDefault("KillStreaks.Tnt", 2);
		config.addDefault("KillStreaks.ElectroRod", 5);
		config.addDefault("KillStreaks.WitherBow", 10);
		config.options().copyDefaults(true);
		SettingsConfig.saveSettingsConfig();

		if (GameData.CurrentMap == null) {
			GameData.CurrentMap = GameManager.pickRandomMap();
		}

	}

	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}

}
