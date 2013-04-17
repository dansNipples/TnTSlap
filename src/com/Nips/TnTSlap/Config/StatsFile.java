package com.Nips.TnTSlap.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Nips.TnTSlap.TnTSlap;

public class StatsFile {
	private static TnTSlap plugin;

	public StatsFile(TnTSlap plugin) {
		StatsFile.plugin = plugin;
	}

	private static FileConfiguration stats = null;
	private static File statsFile = null;

	public static void reloadStats() {
		if (statsFile == null) {
			statsFile = new File(plugin.getDataFolder(), "stats.yml");
		}
		stats = YamlConfiguration.loadConfiguration(statsFile);

		InputStream defConfigStream = plugin.getResource("stats.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			stats.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getStatsFile() {
		if (stats == null) {
			reloadStats();
		}
		return stats;
	}

	public static void saveStats() {
		if (stats == null || statsFile == null) {
			return;
		}
		try {
			getStatsFile().save(statsFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + statsFile, ex);
		}
	}
}
