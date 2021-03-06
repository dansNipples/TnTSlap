package com.Nips.TnTSlap.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.TnTSlap;
import com.Nips.TnTSlap.Utils.GameManager;

public class ArenaConfig {
	private static TnTSlap plugin;

	public ArenaConfig(TnTSlap plugin) {
		ArenaConfig.plugin = plugin;
	}

	private static FileConfiguration arenaConfig = null;
	private static File customArenaFile = null;

	public static void CreateArena(String Name, String CreatorName) {
		if (getArenaConfig().contains(Name)) {
			GameManager.messageTntPlayer(Bukkit.getPlayer(CreatorName), ChatColor.YELLOW + "Arena Already Exists!");
			return;
		}
		getArenaConfig().set(Name, "Made by: " + CreatorName);
		saveArenaConfig();
		GameManager.messageTntPlayer(Bukkit.getPlayer(CreatorName), ChatColor.YELLOW + "Arena Created!");
	}

	public static void DeleteArena(String Name, Player p) {
		Set<String> keys = getArenaConfig().getKeys(false);
		if (!keys.contains(Name) || !getArenaConfig().contains(Name)) {
			p.sendMessage(ChatColor.RED + "No such arena name!");
			return;
		}
		keys.remove(Name);
		// getArenaConfig().getKeys(false).addAll(keys);
		saveArenaConfig();
	}

	public static void SetArenaSpawnPoint(String Name, Player p) {
		int j;
		Location loc = p.getLocation();
		if (!getArenaConfig().contains(Name)) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Arena Does Not Exist!");
			return;
		}
		if (!getArenaConfig().contains(Name + ".1")) {
			j = 1;
		} else {
			j = getPointCount(Name) + 1;
		}

		getArenaConfig().set(Name + "." + j, loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch());
		saveArenaConfig();
		GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Point Set At:");
		GameManager.messageTntPlayer(p, ChatColor.YELLOW + "  X: " + loc.getX() + "     Y:" + loc.getY() + "     Z:" + loc.getZ());
	}

	public static Integer getPointCount(String Name) {
		if (!getArenaConfig().contains(Name)) {
			return null;
		}
		int k = 0;
		Set<String> key = getArenaConfig().getKeys(false);
		if (key.contains(Name)) {
			ConfigurationSection subsection = getArenaConfig().getConfigurationSection(Name);
			k = subsection.getKeys(false).size();
		}
		return k;
	}

	public static void reloadArenaConfig() {
		if (customArenaFile == null) {
			customArenaFile = new File(plugin.getDataFolder(), "arenas.yml");
		}
		arenaConfig = YamlConfiguration.loadConfiguration(customArenaFile);

		InputStream defConfigStream = plugin.getResource("arenas.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			arenaConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getArenaConfig() {
		if (arenaConfig == null) {
			reloadArenaConfig();
		}
		return arenaConfig;
	}

	public static void saveArenaConfig() {
		if (arenaConfig == null || customArenaFile == null) {
			return;
		}
		try {
			getArenaConfig().save(customArenaFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customArenaFile, ex);
		}
	}
}
