package com.Nips.TnTSlap.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.Nips.TnTSlap.Config.SettingsConfig;

public class HelpBookBuilder {

	public static ItemStack BuildBook() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bm = (BookMeta) book.getItemMeta();
		bm.setAuthor("Dan");
		bm.setTitle("Guide To TntSlap");
		ArrayList<String> pages = new ArrayList<String>();
		// Typo?
		pages.add(ChatColor.BLUE + "Bases Of the Game: " + ChatColor.BLACK + "The objective of TntSlap is to knock other players off into the void. Every time you succesfully do that you get a kill. First player to " + SettingsConfig.getSettingsConfig().getInt("Kills_To_Win") + " kills wins.");
		pages.add(ChatColor.BLUE + "Hitting Apparatus: " + ChatColor.BLACK + "When the game first starts you will be holding something in you're hand, this is your hitting apparatus. Use this and only this to hit players with. Your hands will not work.");
		pages.add(ChatColor.BLUE + "Exp Level: " + ChatColor.BLACK + "Your exp level indicates what 'Knockback level' you are at, the higher it is the further you will fly back when hit by another player. When YOU fall off your exp level will rest back to 0");
		pages.add(ChatColor.BLUE + "KillStreaks: " + ChatColor.BLACK + "Current killstreaks are 'Tnt' & 'ElectroRod'. Tnt is rewarded at " + SettingsConfig.getSettingsConfig().getInt("KillStreaks.Tnt") + " kill(s) & ElectroRod at " + SettingsConfig.getSettingsConfig().getInt("KillStreaks.ElectroRod") + " kill(s).");
		pages.add(ChatColor.RED + "Tnt Killstreak: " + ChatColor.BLACK + "This killstreak will knock back any players within a 3 block radius of you. All players knocked off will count as a K0");
		pages.add(ChatColor.RED + "ElectroRod Killstreak: " + ChatColor.BLACK + "While looking at a block when activated will beam down a lighteneing bolt and knockback the nearest player. Limit of 100 blocks!");
		bm.setPages(pages);
		// Lets fix the typo
		// bm.setPage(0, "Using fire");
		book.setItemMeta(bm);
		return book;
	}

}
