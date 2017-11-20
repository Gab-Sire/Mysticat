
package com.multitiers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.domaine.ingame.SurrenderAction;

@Service
public class GameService implements QueueListener {

	public GameQueue gameQueue;
	// Key: userId
	public Map<String, Game> newGameList;
	// Key: userId
	public Map<String, Game> updatedGameList;
	// Key: gameId
	public Map<String, Game> existingGameList;
	// Key: gameId
	public Map<String, ActionList> sentActionLists;

	public GameService() {
	}

	public void initGameQueue() {
		this.gameQueue.addToListeners(this);
		this.gameQueue.initListOfPlayersInQueue();
	}

	public void calculateNextTurnFromActionLists(ActionList playerOneActions, ActionList playerTwoActions) {
		String playerOneId = playerOneActions.getPlayerId();
		String playerTwoId = playerTwoActions.getPlayerId();
		String gameId = playerOneActions.getGameId();
		if (!gameId.equals(playerTwoActions.getGameId())) {
			throw new RuntimeException("Game id mismatch.");
		}
		List<Action> actions = getCompleteSortedActionList(playerOneActions, playerTwoActions);
		Game game = this.existingGameList.get(gameId);

		resolveAllActions(actions, game);

		removedPlayedCards(playerOneActions, playerTwoActions, playerOneId, playerTwoId, game);
		if(!game.getEndedWithSurrender()) {
			game.nextTurn();
		}

		this.existingGameList.put(gameId, game);
		this.updatedGameList.put(playerOneId, game);
		this.updatedGameList.put(playerTwoId, game);

		this.sentActionLists.remove(gameId);

		this.newGameList.remove(playerOneId);
		this.newGameList.remove(playerTwoId);

	}

	private void removePlayedCardsFromPlayerHand(ActionList playerActionList, String playerId, Game game) {
		Integer playerIndex = (game.getPlayers()[0].getPlayerId().equals(playerId)) ? 0 : 1;
		Player currentPlayer = game.getPlayers()[playerIndex];

		List<Integer> indexesOfCardsThatWerePlayed = new ArrayList<Integer>();
		gatherIndexesOfPlayedCards(playerActionList, indexesOfCardsThatWerePlayed);

		removePlayedCards(currentPlayer, indexesOfCardsThatWerePlayed);
	}

	private void resolveAllActions(List<Action> actions, Game game) {
		for (Action action : actions) {
			action.resolve(game);
		}
		if(game.getWinnerPlayerIndex()!=null) {
			existingGameList.remove(game.getGameId());
		}
	}
	
	private void removedPlayedCards(ActionList playerOneActions, ActionList playerTwoActions, String playerOneId,
			String playerTwoId, Game game) {
		removePlayedCardsFromPlayerHand(playerOneActions, playerOneId, game);
		removePlayedCardsFromPlayerHand(playerTwoActions, playerTwoId, game);
	}
	
	private void removePlayedCards(Player currentPlayer, List<Integer> indexesOfCardsThatWerePlayed) {
		Collections.sort(indexesOfCardsThatWerePlayed);
		for (int i = indexesOfCardsThatWerePlayed.size() - 1; i >= 0; --i) {
			currentPlayer.removeCardFromHand(indexesOfCardsThatWerePlayed.get(i));
		}
	}

	private void gatherIndexesOfPlayedCards(ActionList playerActionList, List<Integer> indexesOfCardsThatWerePlayed) {
		for (Action action : playerActionList.getPlayerActions()) {
			if (action instanceof SummonAction) {
				indexesOfCardsThatWerePlayed.add(((SummonAction) action).getIndexOfCardInHand());
			}
		}
	}
	
	private List<Action> getCompleteSortedActionList(ActionList playerOneActions, ActionList playerTwoActions) {
		List<Action> actions = new ArrayList<>();
		actions.addAll(playerOneActions.getPlayerActions());
		actions.addAll(playerTwoActions.getPlayerActions());
		Collections.sort(actions);
		return actions;
	}

	public void replaceKickedPlayerActionsWithSurrender(ActionList currentPlayerActionList) {
		String gameId = currentPlayerActionList.getGameId();
		String playerId = currentPlayerActionList.getPlayerId();
		List<Action> surrenderActionList = new ArrayList<>();
		SurrenderAction surrenderAction = new SurrenderAction();
		Game game = existingGameList.get(gameId);
		Integer playerIndex = (game.getPlayers()[0].getPlayerId().equals(playerId)) ? 0 : 1;
		surrenderAction.setField("kicked");
		surrenderAction.setPlayerIndex(playerIndex);
		surrenderActionList.add(surrenderAction);
		currentPlayerActionList.setPlayerActions(surrenderActionList);
	}
	
	
	public void initDataLists() {
		gameQueue = new GameQueue();
		// Key: userId
		newGameList = new HashMap<String, Game>();
		// Key: userId
		updatedGameList = new HashMap<String, Game>();
		// Key: gameId
		existingGameList = new HashMap<String, Game>();
		// Key: gameId
		sentActionLists = new HashMap<String, ActionList>();
		
		initGameQueue();
	}
	
	//Utilitaire de debugging
	public void printDataListStatus() {
		System.out.println("***********************************");
		System.out.println("Players in queue: "+gameQueue.getNbOfPlayersInQueue());
		System.out.println("New game list: "+newGameList.size());
		System.out.println("Updated game list: "+updatedGameList.size());
		System.out.println("Sent actions list: "+sentActionLists.size());
	}
	
	@Override
	public void queueHasEnoughPlayers() {
		System.out.println("Queue pop");
		// Create game
		// Assigner la game aux 2 joueurs qui devraient la recevoir
		Game game = this.gameQueue.matchFirstTwoPlayersInQueue();
		for (Player player : game.getPlayers()) {
			this.newGameList.put(player.getPlayerId(), game);
		}
		this.existingGameList.put(game.getGameId(), game);
	}
	
	
}

