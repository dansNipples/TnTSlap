package com.Nips.TnTSlap.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Nips.TnTSlap.TnTSlap;

public class SettingsConfig {
	private static TnTSlap plugin;

	public SettingsConfig(TnTSlap plugin) {
		SettingsConfig.plugin = plugin;
	}

	private static FileConfiguration settingsConfig = null;
	private static File customSettingsFile = null;

	public static void reloadSettingsConfig() {
		if (customSettingsFile == null) {
			customSettingsFile = new File(plugin.getDataFolder(), "settings.yml");
		}
		settingsConfig = YamlConfiguration.loadConfiguration(customSettingsFile);

		InputStream defConfigStream = plugin.getResource("settings.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			settingsConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getSettingsConfig() {
		if (settingsConfig == null) {
			reloadSettingsConfig();
		}
		return settingsConfig;
	}

	public static void saveSettingsConfig() {
		if (settingsConfig == null || customSettingsFile == null) {
			return;
		}
		try {
			getSettingsConfig().save(customSettingsFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customSettingsFile, ex);
		}
	}
}
