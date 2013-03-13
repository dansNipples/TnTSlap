package com.Nips.TnTSlap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nips.TnTSlap.Commands.SetLobbyCommand;
import com.Nips.TnTSlap.Commands.TnTSlapCommand;
import com.Nips.TnTSlap.Listeners.Block.BlockBreakListener;
import com.Nips.TnTSlap.Listeners.Block.SignChangeListener;
import com.Nips.TnTSlap.Listeners.Entities.EntityDamageListener;
import com.Nips.TnTSlap.Listeners.Entities.PlayerInteractListener;
import com.Nips.TnTSlap.Listeners.Entities.PlayerMoveListener;

public class TnTSlap extends JavaPlugin {
	/**Sign Configuration**/
		private FileConfiguration arenaConfig = null;
		private File customArenaFile = null;
	/** Command Classes Implementation **/
		TnTSlapCommand SlapCommand = new TnTSlapCommand(this);
		SetLobbyCommand ArenaCommand = new SetLobbyCommand(this);

	/** Configuration Classes Implementation **/
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		FileConfiguration config = this.getArenaConfig();
		/** Event Registration **/
			pm.registerEvents(new PlayerMoveListener(), this);
			pm.registerEvents(new PlayerInteractListener(), this);
			pm.registerEvents(new BlockBreakListener(), this);
			pm.registerEvents(new SignChangeListener(this), this);
			pm.registerEvents(new EntityDamageListener(), this);
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
	/**Sign Configuration**/
	    public void reloadArenaConfig() {
	        if (customArenaFile == null) {
	                customArenaFile = new File(getDataFolder(), "arenas.yml");
	        }
	        arenaConfig = YamlConfiguration.loadConfiguration(customArenaFile);
	
	        InputStream defConfigStream = this.getResource("signs.yml");
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
