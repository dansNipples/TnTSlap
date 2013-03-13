package com.Nips.TnTSlap;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {
	private GameData game = new GameData();

	public void addKill(Player p) {
		int i = game.Getkills(p);
		GameData.Kills.put(p, i + 1);
		Location loc = p.getLocation();
		// Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("random.anvil_land", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1.0F, 1.0F);
		// ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public void addPlayerToGame(Player p) {
		GameData.Kills.put(p, 0);
		GameData.PlayersInGame.add(p);
		GameData.lastToHit.put(p, null);
		setupInv(p);
	}

	public void PlayerFell(Player p) {
		if (game.getLastToHit(p) == null) {
			p.getServer().broadcastMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " Slipped!");

		} else if (game.Getkills(game.getLastToHit(p)) >= 4) {
			p.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + game.getLastToHit(p).getName() + " WON THE GAME!");
			fireworks(p);
		} else {
			addKill(game.getLastToHit(p));
			String a = (ChatColor.GREEN + game.getLastToHit(p).getName() + "[" + game.Getkills(game.getLastToHit(p)) + "]");
			String b = (ChatColor.RED + p.getName() + "[" + game.Getkills(p) + "]");
			p.getServer().broadcastMessage(ChatColor.GREEN + a + ChatColor.YELLOW + " K0'd " + ChatColor.RED + b);

			ChangeLastAtked(p, null);
		}

		p.teleport(p.getWorld().getSpawnLocation());
	}

	public void ChangeLastAtked(Player target, Player attacker) {
		GameData.lastToHit.remove(target.getDisplayName());
		GameData.lastToHit.put(target, attacker);

	}

	public void setupInv(Player p) {
		Inventory inv = p.getInventory();
		inv.clear();
		ItemStack fish = new ItemStack(349);
		ItemMeta im = fish.getItemMeta();
		im.addEnchant(Enchantment.KNOCKBACK, 10, true);
		im.setDisplayName("§b§lSlap O' Fish");
		fish.setItemMeta(im);
		inv.addItem(fish);

	}

	/**************************************************************/
	public void fireworks(final Player p) {
		//
		// new BukkitRunnable() {
		// public void run() {
		Firework fw = (Firework) p.getWorld().spawnEntity(p.getWorld().getSpawnLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		Random r = new Random();

		int rt = r.nextInt(5) + 1;
		Type type = Type.BALL;
		if (rt == 1)
			type = Type.BALL;
		if (rt == 2)
			type = Type.BALL_LARGE;
		if (rt == 3)
			type = Type.BURST;
		if (rt == 4)
			type = Type.CREEPER;
		if (rt == 5)
			type = Type.STAR;

		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(effect);
		fwm.setPower(1);
		fw.setFireworkMeta(fwm);
		// }
		// }.runTaskTimer(p.getServer()., 20, 40);
	}

	public Color getColor(int i) {
		Color c1 = Color.WHITE;
		if (i == 1)
			c1 = Color.AQUA;
		if (i == 2)
			c1 = Color.BLACK;
		if (i == 3)
			c1 = Color.BLUE;
		if (i == 4)
			c1 = Color.FUCHSIA;
		if (i == 5)
			c1 = Color.GRAY;
		if (i == 6)
			c1 = Color.GREEN;
		if (i == 7)
			c1 = Color.LIME;
		if (i == 8)
			c1 = Color.MAROON;
		if (i == 9)
			c1 = Color.NAVY;
		if (i == 10)
			c1 = Color.OLIVE;
		if (i == 11)
			c1 = Color.ORANGE;
		if (i == 12)
			c1 = Color.PURPLE;
		if (i == 13)
			c1 = Color.RED;
		if (i == 14)
			c1 = Color.SILVER;
		if (i == 15)
			c1 = Color.TEAL;
		if (i == 16)
			c1 = Color.WHITE;
		if (i == 17)
			c1 = Color.YELLOW;

		return c1;
	}
}
