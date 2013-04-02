package com.Nips.TnTSlap.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class GameData {
	public static ArrayList<Player> PlayersInGame = new ArrayList<Player>();
	public static Map<Player, Integer> TotalKills = new HashMap<Player, Integer>();
	public static Map<Player, Integer> Killstreak = new HashMap<Player, Integer>();
	public static Map<Player, Player> lastToHit = new HashMap<Player, Player>();
	public static String CurrentMap;
	public static String NextMap;
	public static boolean Started;
	public static boolean Counting;
	public static boolean Pvp;

	public static int Getkills(Player p) {
		return TotalKills.get(p);
	}

	public static int GetKillstreak(Player p) {
		return Killstreak.get(p);
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

	public static String getCurrentMap() {
		return CurrentMap;
	}

	public static String getNextMap() {
		return NextMap;
	}

}
