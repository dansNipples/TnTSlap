package com.Nips.TnTSlap.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameData {
	private static ArrayList<String> PlayersInGame = new ArrayList<String>();
	private static Map<String, Integer> Points = new HashMap<String, Integer>(); // this was TotalKills
	private static Map<String, Integer> Killstreak = new HashMap<String, Integer>();
	private static Map<String, String> lastToHit = new HashMap<String, String>();
	private static String CurrentMap;
	private static String NextMap;
	private static boolean Started;
	private static boolean Pvp;

	/** Players In Game **/
	public static boolean isPlaying(Player p) {
		if (PlayersInGame.contains(p.getName())) {
			return true;
		}
		return false;
	}

	public static void addToGame(Player p) {
		if (isPlaying(p) == true) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "You are already in the game!");
			return;
		}
		PlayersInGame.add(p.getName());
	}

	public static void removeFromGame(Player p) {
		if (isPlaying(p) == false) {
			GameManager.messageTntPlayer(p, ChatColor.YELLOW + "You are not in the game!");
			return;
		}
		PlayersInGame.add(p.getName());
	}

	public static void removeAllInGame() {
		for (String s : PlayersInGame) {
			PlayersInGame.remove(s);
		}
	}

	public static ArrayList<String> getPlayersIngame() {
		return PlayersInGame;
	}

	/** ============================================================================================ **/

	/** Total Kills **/
	public static boolean hasKilled(Player p) {
		if (Points.containsKey(p.getName())) {
			return true;
		}
		return false;
	}

	public static int getPoints(Player p) {
		if (!hasKilled(p)) {
			return 0;
		}
		return Points.get(p.getName());
	}

	public static void addPoint(Player p) {
		int i = getPoints(p);
		Points.put(p.getName(), i + 1);
	}

	public static void removePoint(Player p) {
		int i = getPoints(p);
		Points.put(p.getName(), i - 1);
	}

	public static void removePoints(Player p, int amount) {
		int i = getPoints(p);
		int j = i - amount;
		if (amount > i) {
			j = i;
		}
		Points.put(p.getName(), j);
	}

	/** ============================================================================================ **/

	/** Kill Streaks **/
	public static boolean hasKillstreak(Player p) {
		if (Killstreak.containsKey(p.getName())) {
			return true;
		}
		return false;
	}

	public static int getKillstreak(Player p) {
		if (hasKillstreak(p) == false) {
			return 0;
		}
		return Killstreak.get(p.getName());
	}

	public static void resetKillstreak(Player p) {
		if (hasKillstreak(p) == true) {
			Killstreak.put(p.getName(), 0);
		}
	}

	public static void addToKillstreak(Player p) {
		int i = getKillstreak(p);
		Killstreak.put(p.getName(), i + 1);
	}

	/** ============================================================================================ **/

	/** Last to hit **/
	public static boolean isHit(Player p) {
		if (lastToHit.containsKey(p.getName())) {
			return true;
		}
		return false;
	}

	public static void setLastHit(Player attacker, Player attackee) {
		if (attackee == null) {
			lastToHit.put(attacker.getName(), null);
		} else
			lastToHit.put(attacker.getName(), attackee.getName());
	}

	public static String getLastToHit(Player p) {
		if (isHit(p) == true) {
			return lastToHit.get(p.getName());
		}
		return null;
	}

	/** ============================================================================================ **/

	/** Game Settings **/

	// pvp
	public static boolean isStarted() {
		if (Started == true) {
			return true;
		}
		return false;
	}

	public static void setGameState(boolean b) {
		Started = b;
	}

	public static boolean getGameState() {
		if (Started == true) {
			return true;
		}
		return false;
	}

	// pvp
	public static boolean isPvp() {
		if (Pvp == true) {
			return true;
		}
		return false;
	}

	public static void setPvp(boolean b) {
		Pvp = b;
	}

	/** ============================================================================================ **/

	/** Maps **/
	// current
	public static String getCurrentMap() {
		if (CurrentMap != null) {
			return CurrentMap;
		}
		return null;
	}

	public static void setCurrentMap(String s) {
		CurrentMap = s;
	}

	// next
	public static String getNextMap() {
		if (NextMap != null) {
			return NextMap;
		}
		return null;
	}

	public static void setNextMap(String s) {
		NextMap = s;
	}

	/** ============================================================================================ **/

	/** Other **/
	public static void wipeAllData() {
		PlayersInGame.clear();
		Points.clear();
		Killstreak.clear();
		lastToHit.clear();
	}

	public static void removeFromMaps(Player p) {
		if (PlayersInGame.contains(p.getName())) {
			PlayersInGame.remove(p.getName());
		}
		if (Points.containsKey(p.getName())) {
			Points.remove(p.getName());
		}
		if (Killstreak.containsKey(p.getName())) {
			Killstreak.remove(p.getName());
		}
		if (lastToHit.containsKey(p.getName()) || lastToHit.containsValue(p.getName())) {
			lastToHit.remove(p.getName());
			// remove his last to hit here
		}
	}

	public static void addToMaps(Player p) {
		if (!PlayersInGame.contains(p.getName())) {
			PlayersInGame.add(p.getName());
		}
		if (!Points.containsKey(p.getName())) {
			Points.put(p.getName(), 0);
		}
		if (!Killstreak.containsKey(p.getName())) {
			Killstreak.put(p.getName(), 0);
		}
		if (!lastToHit.containsKey(p.getName())) {
			lastToHit.put(p.getName(), null);
			// remove his last to hit here
		}
	}

	public static void resetPlayerMaps() {
		for (String s : PlayersInGame) {
			Points.put(s, 0);
			Killstreak.put(s, 0);
			lastToHit.put(s, null);
		}
	}

}
