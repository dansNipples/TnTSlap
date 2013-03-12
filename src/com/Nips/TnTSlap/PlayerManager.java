package com.Nips.TnTSlap;

import org.bukkit.entity.Player;

public class PlayerManager {
	private final GameData game = new GameData();

	public void addPlayerToGame(Player player) {
		game.PlayersInGame.add(player);
	}

	public void addKill(Player p) {
		int i = game.Getkills(p);
		game.Kills.put(p, i++);
	}

	public void PlayerFell(Player faller) {
		if (game.getLastToHit(faller) == null) {

		} else
			addKill(game.getLastToHit(faller));
	}
}
