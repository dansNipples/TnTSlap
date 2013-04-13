package com.Nips.TnTSlap.Stats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.entity.Player;

public class Stats {
	private static Map<String, Integer> OverallWins = StatsHandler.loadStatsFromFile();
	public static ArrayList<String> ranks = new ArrayList<String>();

	public static void addWin(Player p) {
		if (!OverallWins.containsKey(p.getName())) {
			OverallWins.put(p.getName(), 1);
			return;
		}
		int i = OverallWins.get(p.getName());
		OverallWins.put(p.getName(), i + 1);
	}

	public static Map<String, Integer> getOverallWins() {
		return OverallWins;
	}

	public static ArrayList<String> getRankList() {
		return ranks;
	}

	public static void organizeRanks() {
		if (OverallWins.keySet() == null) {
			return;
		}
		ValueComparator bvc = new ValueComparator(OverallWins);
		TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(bvc);
		sorted.putAll(OverallWins);
		LinkedHashMap<String, Integer> linked = new LinkedHashMap<String, Integer>();
		linked.putAll(sorted);
		ranks.clear();
		ranks.addAll(linked.keySet());
		StatsHandler.saveSatsToFile(OverallWins);
	}

	public static String getPlayerAtRank(Integer position) {
		if (position == 0 || position > ranks.size()) {
			return "--";
		}
		int i = position - 1;
		if (ranks.get(i) != null) {

			return ranks.get(i);
		}

		return "--";

	}

	public static String getPlayerRank(Player p) {
		String s = p.getName();
		if (!ranks.contains(s)) {
			return "No Rank";
		}
		int i = ranks.indexOf(s) + 1;
		return i + "";
	}

	public static Integer getPlayerWins(Player p) {
		if (!OverallWins.containsKey(p.getName())) {
			return null;
		}
		return OverallWins.get(p.getName());
	}

	// public static void organizeLeaderboard() {
	//
	// HashMap<String, Double> map = new HashMap<String, Double>();
	// ValueComparator bvc = new ValueComparator(map);
	// TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
	// LinkedHashMap<String, Double> linked = new LinkedHashMap<String, Double>();
	//
	// map.put("A", 99.5);
	// map.put("B", 67.4);
	// map.put("C", 67.4);
	// map.put("D", 67.3);
	//
	// System.out.println("unsorted map: " + map);
	//
	// sorted_map.putAll(map);
	// linked.putAll(sorted_map);
	// linked.put("Tester", 1.69);
	// ArrayList<String> sett = new ArrayList<String>();
	// sett.addAll(linked.keySet());
	//
	// System.out.println("results: " + sorted_map);
	// System.out.println("linked in order? " + linked);
	// System.out.println("2nd: " + sett.get(1) + "  3rd:" + sett.get(2));
	//
	// }
}
