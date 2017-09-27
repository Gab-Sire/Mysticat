package com.multitiers.service;

import java.util.Set;
import java.util.TreeSet;

import com.multitiers.util.Constantes;

public class Game {
	private static Player[] players;
	private Integer currentMana;
	private Set<Action> actions;
	
	public Game() {
		//Hard coder player2
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
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
	
}
