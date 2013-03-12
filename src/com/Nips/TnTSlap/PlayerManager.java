package com.Nips.TnTSlap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerManager {

	GameData game;

	public void addPlayerToGame(Player player) {
		game.PlayersInGame.add(player);
	}

	public void addKill(Player p) {
		int i = game.Getkills(p);
		game.Kills.put(p, i++);
	}

	public void PlayerFell(Player faller) {
		if (game.getLastToHit(faller) != null) {
			faller.getServer().broadcastMessage(ChatColor.RED + faller.getDisplayName() + ChatColor.YELLOW + " Slipped!");

		} else {
			String a = (ChatColor.GREEN + game.getLastToHit(faller).getDisplayName() + "[" + game.Getkills(game.getLastToHit(faller)) + "]");
			String b = (ChatColor.RED + faller.getDisplayName() + "[" + game.Getkills(faller) + "]");
			faller.getServer().broadcastMessage(ChatColor.YELLOW + "K0'd" + b);
		}

		faller.teleport(faller.getWorld().getSpawnLocation());
	}

	public void ChangeLastAtked(Player target, Player attacker) {
		game.lastToHit.remove(target.getDisplayName());
		game.lastToHit.put(target, attacker);

		// new BukkitRunnable() {
		// public void run() {
		// game.lastToHit.remove(target);
		// }
		// }.runTaskLater(Bukkit.getPluginManager().getPlugin("TnTSlap"), 200L);
	}
}
