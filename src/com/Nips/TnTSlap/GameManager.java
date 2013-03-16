package com.Nips.TnTSlap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager {
	private static TnTSlap plugin;

	public GameManager(TnTSlap plugin) {
		this.plugin = plugin;
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
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "New Game Starting in 30s!");
	}

	public static void startGame() {
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "game starting..");
		GameData.setPvp(true);
		GameData.setGameSession(true);
		for (Player p : GameData.PlayersInGame) {
			p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation()); // change to picking random spot
			PlayerManager.setupInv(p);
		}
	}

	public static void resetGame() {

		new BukkitRunnable() {
			public void run() {
				startGame();

			}
		}.runTaskLater(plugin, 600);
	}

}
