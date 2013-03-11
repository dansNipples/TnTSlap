package com.Nips.TnTSlap.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinGameEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	protected Player joinee;
	private boolean sessionState;

	public PlayerJoinGameEvent(Player joinee, Boolean sessionState) {
		this.joinee = joinee;
		this.sessionState = sessionState;
	}

	public Player getPlayer() {
		return joinee;
	}

	/**
	 * True = Started False = not Started
	 * 
	 * @return If Game is started or not
	 */
	public boolean getSessionState() {
		return sessionState;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
