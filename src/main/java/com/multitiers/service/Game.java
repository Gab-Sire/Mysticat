package com.multitiers.service;

import com.multitiers.util.Constantes;

public class Game {
	private static Player[] players;
	private Integer currentMana;
	
	
	public Game() {
		//Hard coder player2
		players = new Player[Constantes.MAX_NB_OF_PLAYERS];
		currentMana = 0;
	}
	
	public void nextTurn() {
		if(currentMana<Constantes.MAX_MANA) {
			currentMana++;
		}
	}
	
}
