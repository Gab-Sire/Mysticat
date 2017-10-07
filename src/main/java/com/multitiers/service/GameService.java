package com.multitiers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.PlayableCard;
import com.multitiers.domaine.ingame.PlayableCharacter;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;

@Service
public class GameService implements QueueListener{
	private static final int HERO_FACE_INDEX = -1;
	private static final int NB_OF_PLAYERS_PER_GAME = 2;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private MinionCardRepository minionCardRepository;
	
	public GameQueue gameQueue = new GameQueue();
	public Map<String, Game> gameList = new HashMap<String, Game>();
	
	public GameService() {
	}

	public Game deserializeGame(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
		Gson gson = gsonBuilder.create();

		Game gameFromJson = gson.fromJson(json, Game.class);

		return gameFromJson;
	}

	//TODO Refactor into functions.
	/*
	 * Updates game state, calculating and stashing all changes into Player1's game for further use.
	 * */
	public Game updateGame(Game playerOneGame, Game playerTwoGame) {
		List<Action> actions = getCompleteSortedActionList(playerOneGame, playerTwoGame);
		getPlayerTwoActualHand(playerOneGame, playerTwoGame);
		
		for (Action action : actions) {
			int playerIndex = action.getPlayerIndex();
			int opponentIndex = (playerIndex == 0) ? 1 : 0;
			Player currentPlayer = playerOneGame.getPlayers()[playerIndex];
			Player opponentPlayer = playerOneGame.getPlayers()[opponentIndex];
			if (action instanceof SummonAction) {
				resolveSummonAction(action, currentPlayer);
			} 
			else if (action instanceof AttackAction) {
				resolveAttackAction(action, currentPlayer, opponentPlayer);
			}
		}
		return playerOneGame;
	}

	private List<Action> getCompleteSortedActionList(Game playerOneGame, Game playerTwoGame) {
		List<Action> actions = playerOneGame.getActions();
		actions.addAll(playerTwoGame.getActions());
		Collections.sort(actions);
		return actions;
	}

	private void getPlayerTwoActualHand(Game playerOneGame, Game playerTwoGame) {
		playerOneGame.setPlayerTwoHand(playerTwoGame.getPlayers()[1].getHand());
	}

	private void resolveSummonAction(Action action, Player currentPlayer) {
		int fieldCell = ((SummonAction) action).getFieldCell();
		Minion minion = new Minion(((SummonAction) action).getMinionCard());
		currentPlayer.getField()[fieldCell] = minion;
		System.out.println(minion.getName() + " was played on: " + fieldCell);
	}

	private void resolveAttackAction(Action action, Player currentPlayer, Player opponentPlayer) {
		int attackerIndex = ((AttackAction) action).getAttackerIndex();
		int attackedIndex = ((AttackAction) action).getAttackedIndex();
		int speed = ((AttackAction) action).getSpeed();
		Minion attacker = currentPlayer.getField()[attackerIndex];
		verifySpeed(speed, attacker);
		
		PlayableCharacter attacked;
		if (attackedIndex==HERO_FACE_INDEX) {
			attacked = opponentPlayer.getHero();
			attacked.setHealth(attacked.getHealth()-attacker.getPower());
			if(attacked.isDead()) {
				System.out.println("Congratz grad, you won.");
			}
			return;
		}
		else {
			attacked = opponentPlayer.getField()[attackedIndex];
			if(attacked!=null) {
				attacker.setHealth(attacker.getHealth()-((Minion)attacked).getPower());
				attacked.setHealth(attacked.getHealth()-attacker.getPower());
				if(attacker.isDead()) {
					attacker = null;
				}
				if(attacked.isDead()) {
					attacked = null;
				}
			}
		}
	}

	private void verifySpeed(int speed, Minion attacker) {
		if (attacker.getSpeed() != speed) {
			// TODO Gestion du mismatch
			System.out.println("Error: Mismatch in speed for minion " + attacker.getName());
		}
	}
	

	@Override
	public void queueHasEnoughPlayers() {
		System.out.println("Queue pop");
		//Create game
		//Assigner la game aux 2 joueurs qui devraient la recevoir
	}
}
