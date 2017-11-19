package com.multitiers.domaine.ingame;

import java.util.Collections;
import java.util.List;

/*
 * Objet envoye par un joueur a la fin de son tour. Contient
 * La reference a la partie, son index de joueur dans la partie
 * Ses actions.
 * Est cree en front end
 * */

public class ActionList {
	private List<Action> playerActions;
	private String playerId;
	private String gameId;
	
	public ActionList() {
	}
	
	public List<Action> getPlayerActions() {
		return playerActions;
	}
	public void setPlayerActions(List<Action> playerActions) {
		this.playerActions = playerActions;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public void combineActionList(ActionList otherList) {
		this.playerActions.addAll(otherList.playerActions);
	}
	
	public Boolean containsSurrenderAction(){
		List<Action> actions = getPlayerActions();
		Collections.sort(actions);
		return actions.get(0) instanceof SurrenderAction;
	}
	
	public Boolean isSamePlayerSurrendering(ActionList otherPlayerAction) {
		return (getPlayerId().equals(otherPlayerAction.getPlayerId()) && containsSurrenderAction());
	}
}
