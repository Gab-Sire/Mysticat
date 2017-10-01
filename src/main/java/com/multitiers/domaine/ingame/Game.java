package com.multitiers.domaine.ingame;

import java.util.Set;
import java.util.TreeSet;

import com.multitiers.util.Constantes;

public class Game {
	private Player[] players;
	private Integer currentMana;
	private Set<Action> actions;
	
	public Game() {
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
		currentMana = Constantes.STARTING_MANA;
		actions = new TreeSet<Action>();
	}
	
	public Game(Player player1, Player player2) {
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
		players[0] = player1;
		players[1] = player2;
		currentMana = Constantes.STARTING_MANA;
		actions = new TreeSet<Action>();
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

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}
	
}
