package com.Nips.TnTSlap.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.Nips.TnTSlap.TnTSlap;
import com.Nips.TnTSlap.Config.ArenaConfig;
import com.Nips.TnTSlap.Functions.SpawnFunction;

public class GameManager {
	private static TnTSlap plugin;

	public GameManager(TnTSlap plugin) {
		GameManager.plugin = plugin;
	}

	public static void endGame() {
		GameData.setPvp(false);
		GameData.setGameSession(false);
		for (Player p : GameData.PlayersInGame) {
			GameData.lastToHit.put(p, null);
			GameData.Kills.put(p, 0);

		}
		plugin.time.resumeit();
		resetGame();
	}

	public static void startGame() {
		GameData.setPvp(true);
		GameData.setGameSession(true);
		GameData.CurrentMap = GameData.NextMap;
		for (Player p : GameData.PlayersInGame) {
			PlayerManager.setupInv(p);
			SpawnFunction.SpawnPlayer(p);
		}

	}

	public static void resetGame() {
		GameData.NextMap = pickRandomMap();
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "New Game in 30s. Next Map will be: " + ChatColor.YELLOW + "'" + GameData.NextMap + "'");
		new BukkitRunnable() {
			public void run() {
				startGame();

			}
		}.runTaskLater(plugin, 600);
	}

	public static String pickRandomMap() {
		Set<String> maps = ArenaConfig.getArenaConfig().getKeys(false);
		List<String> mapss = new ArrayList<String>();
		mapss.addAll(maps);
		Random rand = new Random();
		Integer i = rand.nextInt(maps.size());
		String s = mapss.get(i);
		return s;

	}

	public static void setMap(String s, Player p) {
		if (!ArenaConfig.getArenaConfig().contains(s)) {
			p.sendMessage(ChatColor.RED + "No such map. Names are case sensative");
			return;
		}
		GameData.NextMap = s;
		p.sendMessage(ChatColor.GREEN + "Next map set to: " + s);
	}

	public static void announceMessage(String s) {
		for (Player p : GameData.getPlayersInGame()) {
			p.sendMessage(s);
		}
	}

}
