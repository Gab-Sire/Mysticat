package com.multitiers.domaine.ingame;

import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

public class Game {
	private String gameId;
	private Player[] players;
	private Integer currentMana;
	
	public Game() {
		super();
	}
	
	public Game(Player player1, Player player2) {
		this.gameId = ConnectionUtils.generateUUID().toString();
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
		players[0] = player1;
		players[1] = player2;
		currentMana = Constantes.STARTING_MANA;
    	this.nextTurn();
	}
	
	public void nextTurn() {
		if(this.currentMana<Constantes.MAX_MANA) {
			this.currentMana++;
		}
		players[0].drawCard();
		players[1].drawCard();
		players[0].setRemainingMana(this.currentMana);
		players[1].setRemainingMana(this.currentMana);
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
	
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
}
