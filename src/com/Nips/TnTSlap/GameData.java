package com.Nips.TnTSlap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class GameData {
	public ArrayList<Player> PlayersInGame = new ArrayList<Player>();

	public Map<Player, Integer> Kills = new HashMap<Player, Integer>();
	public Map<Player, Player> lastToHit = new HashMap<Player, Player>();

	public int Getkills(Player p) {
		return Kills.get(p);
	}

	public Player getLastToHit(Player p) {
		return lastToHit.get(p);
	}
}
