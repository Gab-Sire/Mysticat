package com.multitiers.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
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
	private static final int PLAYER_ONE_INDEX = 0;
	private static final int PLAYER_TWO_INDEX = 1;
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
	//Key: GameId
	public Map<String, Game> newGameList = new HashMap<String, Game>();
	//Key: userId
	public Map<String, Game> existingGameList = new HashMap<String, Game>();
	
	public GameService() {
	}

	public void initGameQueue() {
		this.gameQueue.addToListeners(this);
	}
	
	//Fonction pour tester la serialization et deserialization d'une Game
	public Game deserializeGame(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
		Gson gson = gsonBuilder.create();

		Game gameFromJson = gson.fromJson(json, Game.class);

		return gameFromJson;
	}
	
	//TODO
	public ActionList deserializeActionList(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		ActionList list = gson.fromJson(json, ActionList.class);
		
		return null;
	}

	/*
	 * Updates game state, calculating and stashing all changes into Player1's game for further use.
	 * */
	public Game updateGame(ActionList playerOneActions, ActionList playerTwoActions) {
		if(playerOneActions.getGameId()!=playerTwoActions.getGameId()) {
			throw new RuntimeException("Game id mismatch.");
		}
		String gameId = playerOneActions.getGameId();
		List<Action> actions = getCompleteSortedActionList(playerOneActions, playerTwoActions);
		
		Game game = this.newGameList.get(gameId);
		
		for (Action action : actions) {
			if(action instanceof SummonAction) {
				resolveSummonAction((SummonAction)action, game);
			}
			else if(action instanceof AttackAction) {
				resolveAttackAction((AttackAction)action, game);
			}
		}
		return game;
	}

	private List<Action> getCompleteSortedActionList(ActionList playerOneActions, ActionList playerTwoActions) {
		List<Action> actions = playerOneActions.getPlayerActions();
		actions.addAll(playerTwoActions.getPlayerActions());
		Collections.sort(actions);
		return actions;
	}

	private void resolveSummonAction(SummonAction summonAction, Game game) {
		Player playerSummoningTheMinion = game.getPlayers()[summonAction.getPlayerIndex()];
		int fieldCell = summonAction.getFieldCellWhereTheMinionIsBeingSummoned();
		Minion minion = new Minion(summonAction.getMinionCard());
		playerSummoningTheMinion.getField()[fieldCell] = minion;
		System.out.println(playerSummoningTheMinion.getName()+" played "+minion.getName() + " on: " + fieldCell);
		
	}

	private void resolveAttackAction(AttackAction attackAction, Game game) {
		Integer playerDeclaringAttackIndex = attackAction.getPlayerIndex();
		Integer opponentPlayerIndex = (playerDeclaringAttackIndex==PLAYER_ONE_INDEX) ? PLAYER_TWO_INDEX : PLAYER_ONE_INDEX;
		Player playerDeclaringAttack = game.getPlayers()[playerDeclaringAttackIndex];
		Player opponentPlayer = game.getPlayers()[opponentPlayerIndex];
		
		int attackerIndex = attackAction.getAttackingMinionIndex();
		int attackedIndex = attackAction.getTargetIndex();
		int speed = attackAction.getSpeed();
		Minion attacker = playerDeclaringAttack.getField()[attackerIndex];
		verifySpeed(speed, attacker);
		
		PlayableCharacter targetOfTheAttack;
		if (attackedIndex==HERO_FACE_INDEX) {
			targetOfTheAttack = opponentPlayer.getHero();
			targetOfTheAttack.setHealth(targetOfTheAttack.getHealth()-attacker.getPower());
			if(targetOfTheAttack.isDead()) {
				System.out.println("Congratz grad, you won.");
			}
			return;
		}
		else {
			targetOfTheAttack = opponentPlayer.getField()[attackedIndex];
			if(targetOfTheAttack!=null) {
				attacker.setHealth(attacker.getHealth()-((Minion)targetOfTheAttack).getPower());
				targetOfTheAttack.setHealth(targetOfTheAttack.getHealth()-attacker.getPower());
				if(attacker.isDead()) {
					attacker = null;
				}
				if(targetOfTheAttack.isDead()) {
					targetOfTheAttack = null;
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
		Game game  = this.gameQueue.matchFirstTwoPlayersInQueue();
		for (Player player : game.getPlayers()) {
			this.newGameList.put(player.getPlayerId(), game);
		}
	}
}
