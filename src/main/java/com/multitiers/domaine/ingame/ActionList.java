package com.multitiers.domaine.ingame;

import java.util.List;

/*
 * Objet envoye par un joueur a la fin de son tour. Contient
 * La reference a la partie, son index de joueur dans la partie
 * Ses actions.
 * Est cree en front end
 * */

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
