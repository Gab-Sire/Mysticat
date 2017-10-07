package com.multitiers.domaine.ingame;

import java.util.List;

import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

public class Game {
	private String gameId;
	private Player[] players;
	private Integer currentMana;
	private Integer currentPlayerIndex;
	
	public Game() {
		super();
	}
	
	public Game(Player player1, Player player2) {
		this.gameId = ConnectionUtils.generateUUID().toString();
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
		player1.setGameId(this.gameId);
		player2.setGameId(this.gameId);
		players[0] = player1;
		players[1] = player2;
		currentMana = Constantes.STARTING_MANA;
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

	public Integer getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(Integer currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
	public void setPlayerTwoHand(List<PlayableCard> hand) {
		this.players[1].setHand(hand);
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
}
