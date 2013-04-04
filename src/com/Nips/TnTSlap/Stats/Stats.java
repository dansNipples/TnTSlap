package com.Nips.TnTSlap.Stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

public class Stats {
	private static Map<Player, Integer> OverallWins = new HashMap<Player, Integer>();

	public static <K, V> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return ((Integer) e2.getValue()).compareTo((Integer) e1.getValue());
			}
		});

		return sortedEntries;
	}
}
