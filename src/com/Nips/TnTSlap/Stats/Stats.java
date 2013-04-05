package com.Nips.TnTSlap.Stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.entity.Player;

public class Stats {
	private static Map<Player, Integer> OverallWins = new HashMap<Player, Integer>();
	public static ArrayList<Player> Top3 = new ArrayList<Player>();

	public static void addWin(Player p) {
		int i = OverallWins.get(p);
		if (!OverallWins.containsKey(p)) {
			OverallWins.put(p, 1);
			return;
		}
		OverallWins.put(p, i++);
	}

	public static Map<Player, Integer> getOverallWins() {
		return OverallWins;
	}

	public static void doit() {

		HashMap<String, Double> map = new HashMap<String, Double>();
		ValueComparator bvc = new ValueComparator(map);
		TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);

		map.put("A", 99.5);
		map.put("B", 67.4);
		map.put("C", 67.4);
		map.put("D", 67.3);

		System.out.println("unsorted map: " + map);

		sorted_map.putAll(map);

		System.out.println("results: " + sorted_map);
		System.out.println("last key: " + sorted_map.lastKey());
		System.out.println("first key: " + sorted_map.firstKey());
		// System.out.println("higher of first: " + sorted_map.);
		// System.out.println("lower of first: " + sorted_map.lowerKey(sorted_map.firstKey()));
		// System.out.println("higher of last: " + sorted_map.higherKey(sorted_map.lastKey()));
		// System.out.println("lower of last: " + sorted_map.lowerKey(sorted_map.lastKey()));
		// System.out.println("ceiling of first: " + sorted_map.ceilingKey(sorted_map.firstKey()));
		// System.out.println("floor of first: " + sorted_map.floorKey(sorted_map.firstKey()));
		// System.out.println("ceiling of last: " + sorted_map.ceilingKey(sorted_map.lastKey()));
		// System.out.println("floor of last: " + sorted_map.floorKey(sorted_map.lastKey()));

	}
}
