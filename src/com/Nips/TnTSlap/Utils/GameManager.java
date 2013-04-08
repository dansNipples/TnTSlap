package com.Nips.TnTSlap.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.Nips.TnTSlap.TnTSlap;
import com.Nips.TnTSlap.Config.ArenaConfig;
import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Functions.SpawnFunction;

public class GameManager {
	private static TnTSlap plugin;

	public GameManager(TnTSlap plugin) {
		GameManager.plugin = plugin;
	}

	public static void endGame() {
		GameData.setPvp(false);
		GameData.setGameState(false);
		GameData.resetPlayerMaps();
		plugin.time.resumeit();
		resetGame();
	}

	public static void startGame() {
		GameData.setPvp(true);
		GameData.setGameState(true);
		GameData.setCurrentMap(GameData.getNextMap());
		// LeaderBoard.enableScoreBoard();
		announceMessage(ChatColor.YELLOW + "Game Starting! First to " + SettingsConfig.getSettingsConfig().getInt("Kills_To_Win") + " kills wins!");
		for (String s : GameData.getPlayersIngame()) {
			PlayerManager.setupInv(Bukkit.getServer().getPlayer(s));
			SpawnFunction.SpawnPlayer(Bukkit.getServer().getPlayer(s));
			Bukkit.getServer().getPlayer(s).setLevel(0);
			Bukkit.getServer().getPlayer(s).setGameMode(GameMode.SURVIVAL);
		}

	}

	public static void resetGame() {
		String LastMap = GameData.getCurrentMap();
		GameData.setNextMap(pickRandomMap());
		Bukkit.getServer().broadcastMessage(ChatColor.RED + "[TntSlap] " + ChatColor.LIGHT_PURPLE + "New Game in 30s. Next Map will be: " + ChatColor.YELLOW + "'" + GameData.getNextMap() + "'");
		new BukkitRunnable() {
			public void run() {
				startGame();
			}
		}.runTaskLater(plugin, 600);
	}

	public static String pickRandomMap() {
		Set<String> maps = ArenaConfig.getArenaConfig().getKeys(false);
		List<String> mapss = new ArrayList<String>();
		if (GameData.getCurrentMap() != null) {
			mapss.remove(GameData.getCurrentMap());
		}
		mapss.addAll(maps);
		Random rand = new Random();
		Integer i = rand.nextInt(maps.size());
		String s = mapss.get(i);
		return s;
	}

	public static void setMap(String s, Player p) {
		if (!ArenaConfig.getArenaConfig().contains(s)) {
			messageTntPlayer(p, ChatColor.YELLOW + "No such map. Names are case sensative");
			return;
		}
		GameData.setNextMap(s);
		messageTntPlayer(p, ChatColor.YELLOW + "Next map set to: " + s);
	}

	public static void announceMessage(String s) {
		for (String i : GameData.getPlayersIngame()) {
			Bukkit.getServer().getPlayer(i).sendMessage(ChatColor.RED + "[TntSlap] " + s);
		}
	}

	public static void messageTntPlayer(Player p, String s) {
		p.sendMessage(ChatColor.RED + "[TntSlap] " + s);
	}
}
