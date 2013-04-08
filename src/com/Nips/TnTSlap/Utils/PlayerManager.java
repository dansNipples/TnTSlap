package com.Nips.TnTSlap.Utils;

import net.minecraft.server.v1_5_R2.Packet62NamedSoundEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Functions.SpawnFunction;
import com.Nips.TnTSlap.KillStreaks.KillStreaks;
import com.Nips.TnTSlap.Stats.Stats;
import com.Nips.TnTSlap.Utils.Combat.ExpHandler;

public class PlayerManager {

	public static void addPlayerToGame(Player p) {
		if (GameData.isPlaying(p)) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Already In Game!");
			return;
		}
		p.setGameMode(GameMode.SURVIVAL);
		GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Joined Game");
		GameData.addToMaps(p);
		// LeaderBoard.enablePlayerScoreBoard(p);
		SpawnFunction.SpawnPlayer(p);
		setupInv(p);
		ExpHandler.resetLevel(p);
	}

	public static void removePlayerFromGame(Player p) {
		if (!GameData.isPlaying(p)) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Not in Game!");
			return;
		}
		GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Left Game");
		GameData.removeFromMaps(p);
		KillStreaks.canPlayerDoubleJump.remove(p);
		// LeaderBoard.disableScoreBoard(p);
		p.getInventory().clear();
		p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
		ExpHandler.resetLevel(p);
	}

	public static void PlayerFell(Player p) {
		if (GameData.getGameState()) {
			if (GameData.getLastToHit(p) == null) {
				GameManager.announceMessage(ChatColor.YELLOW + p.getName() + ChatColor.RED + " Slipped!");
			} else {
				addKill(p, Bukkit.getServer().getPlayer(GameData.getLastToHit(p)));
			}
			GameData.resetKillstreak(p);
		}

		SpawnFunction.SpawnPlayer(p);
	}

	public static void ChangeLastAtked(Player target, Player attacker) {
		if (attacker == target) {
			return;
		}
		GameData.setLastHit(target, attacker);

		// new BukkitRunnable() {
		//
		// @Override
		// public void run() {
		// GameData.lastToHit.put(target, null);
		// }
		//
		// }.runTaskLater(plugin, 120);
	}

	public static void addKill(Player target, Player attacker) {
		GameData.addPoint(attacker);
		GameData.addToKillstreak(attacker);
		// LeaderBoardHandler.getStats();
		Location loc = attacker.getLocation();
		Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("note.pling", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 2.0F, 1.2F);
		((CraftPlayer) attacker).getHandle().playerConnection.sendPacket(packet);
		if (GameData.getPoints(attacker) >= SettingsConfig.getSettingsConfig().getInt("Kills_To_Win")) {
			GameManager.announceMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + attacker.getName() + ChatColor.GREEN + " Won The Game!");
			Stats.addWin(attacker);
			GameManager.endGame();
		} else {
			String a = (ChatColor.GREEN + attacker.getName() + "[" + GameData.getPoints(attacker) + "]");
			String b = (ChatColor.RED + target.getName() + "[" + GameData.getPoints(target) + "]");
			int kills = GameData.getPoints(attacker);
			int max = SettingsConfig.getSettingsConfig().getInt("Kills_To_Win");
			int maxHalf = Math.round((max / 2));
			int maxFourth = Math.round((max / 4) * 3);
			int last = max - 1;
			GameManager.messageTntPlayer(attacker, ChatColor.YELLOW + "You knocked off player: " + b);
			GameManager.messageTntPlayer(attacker, ChatColor.YELLOW + "You now have " + ChatColor.RED + GameData.getPoints(attacker) + ChatColor.YELLOW + " Knock-Outs!");
			GameManager.messageTntPlayer(attacker, ChatColor.YELLOW + "KillStreak of: " + ChatColor.RED + GameData.getKillstreak(attacker));
			GameManager.messageTntPlayer(target, ChatColor.YELLOW + "Knocked off by player: " + ChatColor.RED + attacker.getName() + "[" + GameData.getPoints(attacker) + "]" + ChatColor.YELLOW + " KillStreak Reset.");
			if (kills == maxHalf) {
				GameManager.announceMessage(ChatColor.YELLOW + "Player " + ChatColor.RED + attacker.getName() + ChatColor.YELLOW + " has " + (max - maxHalf) + " Knock-Outs to go!");
			}
			if (kills == maxFourth) {
				GameManager.announceMessage(ChatColor.YELLOW + "Player " + ChatColor.RED + attacker.getName() + ChatColor.YELLOW + " has " + (max - maxFourth) + " Knock-Outs to go!");
			}
			if (kills == last) {
				GameManager.announceMessage(ChatColor.YELLOW + "Player " + ChatColor.RED + attacker.getName() + ChatColor.YELLOW + " has " + (max - last) + " Knock-Out to go!");
			}
			KillStreaks.CheckForKillstreak(attacker, GameData.getKillstreak(attacker));
			ExpHandler.resetLevel(target);
		}
		ChangeLastAtked(target, null);
	}

	/************************* Inventory Stuffs **********************/

	public static void setupInv(Player p) {
		Inventory inv = p.getInventory();
		inv.clear();
		inv.addItem(getHittingApperatus(p));
		p.updateInventory(); // leave this, it somehow fixes a bug.
	}

	public static ItemStack getHittingApperatus(Player p) {
		ItemStack is = new ItemStack(Material.getMaterial(349));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§b§lSlap O' Fish");
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		if (p.hasPermission("tntslap.item.butter")) {
			is = new ItemStack(Material.GOLD_INGOT);
			im.setDisplayName("§6§lO' MAJESTIC BUTTER");
			p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		}
		is.setItemMeta(im);

		return is;
	}

}
