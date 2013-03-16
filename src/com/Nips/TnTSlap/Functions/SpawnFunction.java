package com.Nips.TnTSlap.Functions;

import org.bukkit.entity.Player;

import com.Nips.TnTSlap.GameData;

public class SpawnFunction {
	public static void getSpawnPoints(String sign) {
		for (Player p : GameData.PlayersInGame) {
			p.teleport(RandomSpotFunction.grabRandomSpot(sign));
		}
	}
}
