package com.multitiers.domaine.ingame;

import java.util.ArrayList;
import java.util.List;

import com.multitiers.util.Constantes;

public class Game {
	private Player[] players;
	private Integer currentMana;
	private List<Action> actions;
	private Integer currentPlayerIndex;
	
	public Game() {
		super();
	}
	
	public Game(Player player1, Player player2) {
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
		players[0] = player1;
		players[1] = player2;
		currentMana = Constantes.STARTING_MANA;
		actions = new ArrayList<Action>();
	}
	
	public void nextTurn() {
		if(currentMana<Constantes.MAX_MANA) {
			currentMana++;
		}
		players[0].drawCard();
		players[1].drawCard();
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Integer getCurrentMana() {
		return currentMana;
	}

	public void setCurrentMana(Integer currentMana) {
		this.currentMana = currentMana;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Integer getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(Integer currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
	public void setPlayerTwoHand(List<PlayableCard> hand) {
		this.players[1].setHand(hand);
	}
	

}
