package com.Nips.TnTSlap.Utils;

import net.minecraft.server.v1_5_R1.Packet62NamedSoundEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Functions.SpawnFunction;

public class PlayerManager {

	public static void addPlayerToGame(Player p) {
		if (GameData.PlayersInGame.contains(p)) {
			p.sendMessage(ChatColor.RED + "Already in Game!");
			return;
		}
		p.sendMessage(ChatColor.YELLOW + "Joined Game");
		GameData.Kills.put(p, 0);
		GameData.PlayersInGame.add(p);
		GameData.lastToHit.put(p, null);
		SpawnFunction.SpawnPlayer(p);
		setupInv(p);
	}

	public static void removePlayerFromGame(Player p) {
		if (!GameData.PlayersInGame.contains(p)) {
			p.sendMessage(ChatColor.RED + "Not in Game!");
			return;
		}
		p.sendMessage(ChatColor.YELLOW + "Left Game");
		GameData.Kills.remove(p);
		GameData.PlayersInGame.remove(p);
		GameData.lastToHit.remove(p);
		p.getInventory().clear();
		p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
	}

	public static void PlayerFell(Player p) {
		if (GameData.Started == true) {
			if (GameData.getLastToHit(p) == null) {
				GameManager.announceMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " Slipped!");
			} else {
				addKill(p, GameData.getLastToHit(p));
			}
		}

		SpawnFunction.SpawnPlayer(p);
	}

	public static void ChangeLastAtked(final Player target, Player attacker) {
		GameData.lastToHit.remove(target);
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
		GameData.Kills.put(attacker, i + 1);
		Location loc = attacker.getLocation();
		Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("note.pling", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0.2F, 0.9F);
		((CraftPlayer) attacker).getHandle().playerConnection.sendPacket(packet);
		if (GameData.Getkills(attacker) >= SettingsConfig.getSettingsConfig().getInt("Kills_To_Win")) {
			GameManager.announceMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + attacker.getName() + ChatColor.GREEN + " Won The Game!");
			GameManager.endGame();
		} else {
			String a = (ChatColor.GREEN + attacker.getName() + "[" + GameData.Getkills(attacker) + "]");
			String b = (ChatColor.RED + target.getName() + "[" + GameData.Getkills(target) + "]");
			GameManager.announceMessage(ChatColor.GREEN + a + ChatColor.YELLOW + " K0'd " + ChatColor.RED + b);
			KillStreaks.CheckForKillstreak(attacker, GameData.Getkills(attacker));

		}
		ChangeLastAtked(target, null);
	}

	public static void setupInv(Player p) {
		Inventory inv = p.getInventory();
		inv.clear();
		inv.addItem(getHittingApperatus(p));

	}

	public static ItemStack getHittingApperatus(Player p) {
		ItemStack is = new ItemStack(Material.getMaterial(349));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§b§lSlap O' Fish");
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		if (p.hasPermission("tntslap.item.butter")) {
			is = new ItemStack(Material.GOLD_INGOT);
			im.setDisplayName("§6§lO' MAJESTIC BUTTER");
		}
		is.setItemMeta(im);

		return is;
	}

}
