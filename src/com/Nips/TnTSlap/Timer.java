package com.Nips.TnTSlap;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Timer implements Runnable {
	private TnTSlap plugin;
	private int id = -1;

	public Timer(TnTSlap instance) {
		plugin = instance;
	}

	public void resumeit() {

		j = 0;
		id = Bukkit.getServer().getScheduler().runTaskTimer(plugin, this, 0L, 40L).getTaskId();

	}

	public void stopit() {
		if (id != -1) {
			Bukkit.getScheduler().cancelTask(id);
		}

	}

	/**
	 * Do stuff when scheduler tells task to run
	 */
	public int j = 0;

	@Override
	public void run() {
		if (j < 10) {
			fireworks(Bukkit.getServer().getWorld("world"));
			j++;
		} else {
			j = 0;
			stopit();
		}

	}

	public void fireworks(final World world) {
		//
		Firework fw = (Firework) world.spawnEntity(world.getSpawnLocation(), EntityType.FIREWORK);
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
	}

	public Color getColor(int i) {
		Color c1 = Color.ORANGE;
		if (i == 1)
			c1 = Color.AQUA;
		if (i == 2)
			c1 = Color.GREEN;
		if (i == 3)
			c1 = Color.BLUE;
		if (i == 4)
			c1 = Color.FUCHSIA;
		if (i == 5)
			c1 = Color.YELLOW;
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
			c1 = Color.LIME;
		if (i == 17)
			c1 = Color.YELLOW;

		return c1;
	}
	/**
	 * Remove task from scheduler
	 * 
	 * @return True if successfully stopped. Else false.
	 */

}