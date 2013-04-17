package com.Nips.TnTSlap.Stats;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import com.Nips.TnTSlap.Config.StatsFile;

public class StatsHandler {

	public static void saveSatsToFile(Map<String, Integer> map) {
		for (Entry<String, Integer> entry : map.entrySet()) {
			StatsFile.getStatsFile().set(entry.getKey(), entry.getValue());
		}
		StatsFile.saveStats();
	}

	public static HashMap<String, Integer> loadStatsFromFile() {
		FileConfiguration stats = StatsFile.getStatsFile();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Set<String> KeyMap = stats.getKeys(false);
		for (String s : KeyMap) {
			map.put(s, stats.getInt(s));
		}

		return map;
	}

}
