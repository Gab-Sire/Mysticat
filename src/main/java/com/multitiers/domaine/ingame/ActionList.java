package com.multitiers.domaine.ingame;

import java.util.List;

public class ActionList {
	private List<Action> playerActions;
	private Integer playerIndex;
	private String gameId;
	
	public ActionList() {
	}
	
	public List<Action> getPlayerActions() {
		return playerActions;
	}
	public void setPlayerActions(List<Action> playerActions) {
		this.playerActions = playerActions;
	}
	public Integer getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(Integer playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
}
