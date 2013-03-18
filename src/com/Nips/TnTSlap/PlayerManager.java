package com.Nips.TnTSlap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {
	private static TnTSlap plugin;

	public PlayerManager(TnTSlap plugin) {
		PlayerManager.plugin = plugin;
	}

	public static void addPlayerToGame(Player p) {
		GameData.Kills.put(p, 0);
		GameData.PlayersInGame.add(p);
		GameData.lastToHit.put(p, null);
		setupInv(p);
	}

	public static void PlayerFell(Player p) {
		if (GameData.getLastToHit(p) == null) {
			if (GameData.Started == true) {
				p.getServer().broadcastMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " Slipped!");
			}
		} else {
			addKill(p, GameData.getLastToHit(p));
		}

		p.teleport(p.getWorld().getSpawnLocation());
	}

	public static void ChangeLastAtked(Player target, Player attacker) {
		GameData.lastToHit.remove(target);
		GameData.lastToHit.put(target, attacker);
	}

	public static void addKill(Player target, Player attacker) {
		int i = GameData.Getkills(attacker);
		GameData.Kills.put(attacker, i + 1);
		Location loc = attacker.getLocation();
		// Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("random.anvil_land", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0.2F, 0.9F);
		// ((CraftPlayer) attacker).getHandle().playerConnection.sendPacket(packet);
		if (GameData.Getkills(attacker) >= 5) {
			attacker.getServer().broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + attacker.getName() + ChatColor.LIGHT_PURPLE + " Won The Game");
			GameManager.endGame();
		} else {
			String a = (ChatColor.GREEN + attacker.getName() + "[" + GameData.Getkills(attacker) + "]");
			String b = (ChatColor.RED + target.getName() + "[" + GameData.Getkills(target) + "]");
			target.getServer().broadcastMessage(ChatColor.GREEN + a + ChatColor.YELLOW + " K0'd " + ChatColor.RED + b);
			if (GameData.Getkills(attacker) == 2) {
				KillStreaks.rewardKillStreak(attacker, 2);
			}

		}
		ChangeLastAtked(target, null);
	}

	public static void setupInv(Player p) {
		Inventory inv = p.getInventory();
		inv.clear();
		ItemStack fish = new ItemStack(349);
		ItemMeta im = fish.getItemMeta();
		im.addEnchant(Enchantment.KNOCKBACK, 10, true);
		im.setDisplayName("§b§lSlap O' Fish");
		fish.setItemMeta(im);
		inv.addItem(fish);

	}

}
