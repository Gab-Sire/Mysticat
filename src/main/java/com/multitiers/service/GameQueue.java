package com.multitiers.service;

import java.util.ArrayList;
import java.util.List;

import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Player;

public class GameQueue {
	private List<Player> listOfPlayersInQueue;
	private List<QueueListener> listeners;
	
	public synchronized void addToQueue(Player player) {
		if (this.listOfPlayersInQueue==null) {
			this.listOfPlayersInQueue = new ArrayList<Player>();
		}
		
	    for (Player playerAlreadyInQueue : this.listOfPlayersInQueue) {
	    	if(playerAlreadyInQueue.getPlayerId()==player.getPlayerId()) {
	    		return;
	    	}
	    }
		
		this.listOfPlayersInQueue.add(player);
		if (this.listOfPlayersInQueue.size()>=2) {
		    for (QueueListener listener : this.listeners) {
		    	listener.queueHasEnoughPlayers();
		    }
		}
	}
	
	public synchronized void removeFromQueue(Player player) {
		listOfPlayersInQueue.remove(player);
	}
	
	public void addToListeners(QueueListener listener) {
		if(this.listeners==null) {
			this.listeners = new ArrayList<QueueListener>();
		}
		this.listeners.add(listener);
	}
	
	public Game matchFirstTwoPlayersInQueue() {
		Player player1 = this.listOfPlayersInQueue.get(0);
		Player player2 = this.listOfPlayersInQueue.get(1);
		
		Game game = new Game(player1, player2);
		
		this.listOfPlayersInQueue.remove(0);
		this.listOfPlayersInQueue.remove(0);
		
		return game;
	}

}
