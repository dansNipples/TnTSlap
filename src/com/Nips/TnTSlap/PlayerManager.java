package com.Nips.TnTSlap;

import org.bukkit.entity.Player;

import com.Nips.TnTSlap.Objects.PlayerLists;

public class PlayerManager {
	private final PlayerLists pl = new PlayerLists();

	public void addPlayerToGame(Player player) {
		pl.PlayersInGame.add(player);
	}
}
