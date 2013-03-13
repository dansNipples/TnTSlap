package com.Nips.TnTSlap.Functions;

import org.bukkit.ChatColor;
import org.bukkit.World;

import com.Nips.TnTSlap.GameData;

public class SpawnFunction {
	GameData game;
	RandomSpotFunction rand;
	World world;
	public void getSpawnPoints(String sign){
	    for (int x = 0; x <= game.PlayersInGame.size(); x++) {
	    	game.PlayersInGame.get(x).teleport(rand.grabRandomSpot(game.PlayersInGame.get(x).getLocation(), sign));
	    }
	}
}
