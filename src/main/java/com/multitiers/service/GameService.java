package com.multitiers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class GameService {
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

	private List<Player> playersInQueue;

	public GameService() {
	}

	public void initQueue() {
		this.playersInQueue = new ArrayList<Player>();
	}

	public synchronized void addToGameQueue(Player player) {
		this.playersInQueue.add(player);
		if (playersInQueue.size() >= NB_OF_PLAYERS_PER_GAME) {
			Player player1 = playersInQueue.get(0);
			Player player2 = playersInQueue.get(1);
			startGame(player1, player2);
		}
	}

	public synchronized void startGame(Player player1, Player player2) {

	}

	public Game deserializeGame(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
		Gson gson = gsonBuilder.create();

		Game gameFromJson = gson.fromJson(json, Game.class);

		return gameFromJson;
	}

	//TODO Refactor into functions.
	public Game updateGame(Game currentGame) {
		List<Action> actions = currentGame.getActions();
		Collections.sort(actions);

		for (Action action : actions) {
			int playerIndex = action.getPlayerIndex();
			int opponentIndex = (playerIndex == 0) ? 1 : 0;
			Player currentPlayer = currentGame.getPlayers()[playerIndex];
			Player opponentPlayer = currentGame.getPlayers()[opponentIndex];
			if (action instanceof SummonAction) {
				resolveSummonAction(action, currentPlayer);
			} else if (action instanceof AttackAction) {
				resolveAttackAction(action, currentPlayer, opponentPlayer);
			}
		}
		return currentGame;
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

		}
		else {
			attacked = opponentPlayer.getField()[attackedIndex];
			attacker.setHealth(attacker.getHealth()-((Minion)attacked).getPower());
		}
		attacked.setHealth(attacked.getHealth()-attacker.getPower());
	}

	private void verifySpeed(int speed, Minion attacker) {
		if (attacker.getSpeed() != speed) {
			// TODO Gestion du mismatch
			System.out.println("Error: Mismatch in speed for minion " + attacker.getName());
		}
	}
}
