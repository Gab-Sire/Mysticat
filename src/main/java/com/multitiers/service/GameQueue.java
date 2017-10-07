package com.multitiers.service;

import java.util.ArrayList;
import java.util.List;

import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Player;

public class GameQueue {
	private List<Player> gameQueue;
	private List<QueueListener> listeners;
	
	
	
	public synchronized void addToQueue(Player player) {
		if(this.gameQueue==null) {
			this.gameQueue = new ArrayList<Player>();
		}
		this.gameQueue.add(player);
		if(this.gameQueue.size()>=2) {
		    for (QueueListener listener : this.listeners) {
		    	listener.queueHasEnoughPlayers();
		    }
		}
	}
	
	public synchronized void removeFromQueue(Player player) {
		gameQueue.remove(player);
	}
	
	public void addToListeners(QueueListener listener) {
		if(this.listeners==null) {
			this.listeners = new ArrayList<QueueListener>();
		}
		this.listeners.add(listener);
	}
	
	public Game matchFirstTwoPlayersInQueue() {
		Player player1 = this.gameQueue.get(0);
		Player player2 = this.gameQueue.get(1);
		Game game = new Game(player1, player2);
		
		
		return null;
	}

}
