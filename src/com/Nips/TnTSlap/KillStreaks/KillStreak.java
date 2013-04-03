package com.Nips.TnTSlap.KillStreaks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Nips.TnTSlap.Config.SettingsConfig;
import com.Nips.TnTSlap.Utils.GameData;
import com.Nips.TnTSlap.Utils.GameManager;

public class KillStreak {
	
	public KillStreak TnT;
	public KillStreak ElectroRod;

	public static void rewardKillStreak(Player p, int kills) {
		//ElectroRodKillStreak.checkIfApplicable(p, kills);
	}

	public static void CheckForKillstreak(Player p, int kills) {
			rewardKillStreak(p, kills);
			GameManager.messageTntPlayer(p, ChatColor.GREEN + "KillStreak reward earnt! Right click to use!");
	}
}
