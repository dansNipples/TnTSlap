package com.Nips.TnTSlap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class GameData {
	public static ArrayList<Player> PlayersInGame = new ArrayList<Player>();

	public static Map<Player, Integer> Kills = new HashMap<Player, Integer>();
	public static Map<Player, Player> lastToHit = new HashMap<Player, Player>();

	public int Getkills(Player p) {
		return Kills.get(p);
	}

	public Player getLastToHit(Player p) {
		return lastToHit.get(p);
	}
}
