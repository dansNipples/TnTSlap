package com.Nips.TnTSlap.Utils;

import net.minecraft.server.v1_5_R1.Packet62NamedSoundEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Functions.SpawnFunction;
import com.Nips.TnTSlap.KillStreaks.KillStreaks;
import com.Nips.TnTSlap.Utils.Combat.ExpHandler;

public class PlayerManager {

	public static void addPlayerToGame(Player p) {
		if (GameData.PlayersInGame.contains(p)) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Already In Game!");
			return;
		}
		p.setGameMode(GameMode.SURVIVAL);
		GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Joined Game");
		GameData.TotalKills.put(p, 0);
		GameData.Killstreak.put(p, 0);
		GameData.PlayersInGame.add(p);
		GameData.lastToHit.put(p, null);
		SpawnFunction.SpawnPlayer(p);
		setupInv(p);
		ExpHandler.resetLevel(p);
	}

	public static void removePlayerFromGame(Player p) {
		if (!GameData.PlayersInGame.contains(p)) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Not in Game!");
			return;
		}
		GameManager.messageTntPlayer(p, ChatColor.YELLOW + "Left Game");
		GameData.TotalKills.remove(p);
		GameData.Killstreak.remove(p);
		GameData.PlayersInGame.remove(p);
		GameData.lastToHit.remove(p);
		p.getInventory().clear();
		p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
		ExpHandler.resetLevel(p);
	}

	public static void PlayerFell(Player p) {
		if (GameData.Started == true) {
			if (GameData.getLastToHit(p) == null) {
				GameManager.announceMessage(ChatColor.YELLOW + p.getName() + ChatColor.RED + " Slipped!");
			} else {
				addKill(p, GameData.getLastToHit(p));
			}
			GameData.Killstreak.put(p, 0);
		}

		SpawnFunction.SpawnPlayer(p);
	}

	public static void ChangeLastAtked(final Player target, Player attacker) {
		GameData.lastToHit.remove(target);
		if (attacker == target) {
			return;
		}
		GameData.lastToHit.put(target, attacker);

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
		int i = GameData.Getkills(attacker);
		int j = GameData.GetKillstreak(attacker);
		GameData.TotalKills.put(attacker, i + 1);
		GameData.Killstreak.put(attacker, j + 1);
		Location loc = attacker.getLocation();
		Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("note.pling", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 2.0F, 1.2F);
		((CraftPlayer) attacker).getHandle().playerConnection.sendPacket(packet);
		if (GameData.Getkills(attacker) >= SettingsConfig.getSettingsConfig().getInt("Kills_To_Win")) {
			GameManager.announceMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + attacker.getName() + ChatColor.GREEN + " Won The Game!");
			GameManager.endGame();
		} else {
			String a = (ChatColor.GREEN + attacker.getName() + "[" + GameData.Getkills(attacker) + "]");
			String b = (ChatColor.RED + target.getName() + "[" + GameData.Getkills(target) + "]");
			GameManager.announceMessage(ChatColor.GREEN + a + ChatColor.YELLOW + " K0'd " + ChatColor.RED + b);
			KillStreaks.CheckForKillstreak(attacker, GameData.GetKillstreak(attacker));
			ExpHandler.resetLevel(target);

		}
		ChangeLastAtked(target, null);
	}

	/************************* Inventory Stuffs **********************/

	public static void setupInv(Player p) {
		Inventory inv = p.getInventory();
		inv.clear();
		inv.addItem(getHittingApperatus(p));
		p.updateInventory();
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
