package com.Nips.TnTSlap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.SetLobbyCommand;
import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Listeners.BlockListener;
import com.Nips.TnTSlap.Listeners.EntityListener;

public class TnTSlap extends JavaPlugin {
	/** Sign Configuration **/
	private FileConfiguration arenaConfig = null;
	private File customArenaFile = null;
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

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		FileConfiguration config = this.getArenaConfig();
		/** Event Registration **/
		pm.registerEvents(entitylistener, this);
		pm.registerEvents(blocklistener, this);

		/** Command Registration **/
		this.getCommand("tntslap").setExecutor(SlapCommand);
		this.getCommand("setarena").setExecutor(ArenaCommand);
		config.addDefault("Arenas.MaxArenaPoints", 10);
		config.options().copyDefaults(true);
		saveArenaConfig();
		getLogger().info("Enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disable");
	}

	/** Sign Configuration **/
	public void reloadArenaConfig() {
		if (customArenaFile == null) {
			customArenaFile = new File(getDataFolder(), "arenas.yml");
		}
		arenaConfig = YamlConfiguration.loadConfiguration(customArenaFile);

		InputStream defConfigStream = this.getResource("arenas.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			arenaConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getArenaConfig() {
		if (arenaConfig == null) {
			this.reloadArenaConfig();
		}
		return arenaConfig;
	}

	public void saveArenaConfig() {
		if (arenaConfig == null || customArenaFile == null) {
			return;
		}
		try {
			getArenaConfig().save(customArenaFile);
		} catch (IOException ex) {
			this.getLogger().log(Level.SEVERE, "Could not save config to " + customArenaFile, ex);
		}
	}
}
