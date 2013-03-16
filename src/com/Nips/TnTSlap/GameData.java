package com.Nips.TnTSlap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class GameData {
	public static ArrayList<Player> PlayersInGame = new ArrayList<Player>();
	public static Map<Player, Integer> Kills = new HashMap<Player, Integer>();
	public static Map<Player, Player> lastToHit = new HashMap<Player, Player>();
	public static boolean Started;
	public static boolean Pvp;

	public static int Getkills(Player p) {
		return Kills.get(p);
	}

	public static Player getLastToHit(Player p) {
		return lastToHit.get(p);
	}

	public static ArrayList<Player> getPlayersInGame() {
		return PlayersInGame;
	}

	public static void setGameSession(boolean seshState) {
		Started = seshState;
	}

	public static boolean IsGameInSession() {
		return Started;
	}

	public static void setPvp(boolean pvp) {
		Pvp = pvp;
	}

	public static boolean isPvpEnabled() {
		return Pvp;
	}

	public static int getPlayerSize() {
		return PlayersInGame.size();
	}

}
