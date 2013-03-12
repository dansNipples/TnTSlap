package com.Nips.TnTSlap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class GameData {
	public ArrayList<Player> PlayersInGame = new ArrayList<Player>();

	public Map<Player, Integer> Kills = new HashMap<Player, Integer>();
	public Map<String, String> lastToHit = new HashMap<String, String>();

	public int Getkills(Player p) {
		return Kills.get(p);
	}

	public String getLastToHit(String s) {
		return lastToHit.get(s);
	}
}
