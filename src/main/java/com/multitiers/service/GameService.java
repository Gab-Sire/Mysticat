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
import com.multitiers.domaine.entity.MinionCard;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Hero;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.PlayableCard;
import com.multitiers.domaine.ingame.PlayableCharacter;
import com.multitiers.domaine.ingame.PlayableMinionCard;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;

@Service
public class GameService implements QueueListener {
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
	// Key: userId
	public Map<String, Game> newGameList = new HashMap<String, Game>();
	// Key: userId
	public Map<String, Game> updatedGameList = new HashMap<String, Game>();
	// Key: gameId
	public Map<String, Game> existingGameList = new HashMap<String, Game>();
	// Key: gameId
	public Map<String, ActionList> sentActionLists = new HashMap<String, ActionList>();

	public GameService() {
	}

	public void initGameQueue() {
		this.gameQueue.addToListeners(this);
	}

	// Fonction pour tester la serialization et deserialization d'une Game
	public Game deserializeGameFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
		Gson gson = gsonBuilder.create();

		Game gameFromJson = gson.fromJson(json, Game.class);

		return gameFromJson;
	}

	
	public ActionList deserializeActionListFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer())
				.registerTypeAdapter(Action.class, new ActionDeserializer()).create();
		ActionList list = gson.fromJson(json, ActionList.class);

		return list;
	}

	public String deserializeStringFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		String username = gson.fromJson(json, String.class);

		return username;

	}

	public void calculateNextTurnFromActionLists(ActionList playerOneActions, ActionList playerTwoActions) {
		String playerOneId = playerOneActions.getPlayerId();
		String playerTwoId = playerTwoActions.getPlayerId();
		
		String gameId = playerOneActions.getGameId();
		if (!gameId.equals(playerTwoActions.getGameId())) {
			throw new RuntimeException("Game id mismatch.");
		}
		if (playerOneId.equals(playerTwoId)) {
			throw new RuntimeException("Duplicate action submission.");
		}
		List<Action> actions = getCompleteSortedActionList(playerOneActions, playerTwoActions);
		Game game = this.existingGameList.get(gameId);
		
		resolveAllActions(actions, game);
		
		removePlayedMinionCardsFromPlayerHand(playerOneActions, playerOneId, game);
		removePlayedMinionCardsFromPlayerHand(playerOneActions, playerTwoId, game);
		game.nextTurn();
		
		this.existingGameList.put(gameId, game);
		this.updatedGameList.put(playerOneId, game);
		this.updatedGameList.put(playerTwoId, game);
				
		this.sentActionLists.remove(gameId);
				
		this.newGameList.remove(playerOneId);
		this.newGameList.remove(playerTwoId);
		System.out.println("Next turn calculated...");		
		
	}

	private void removePlayedMinionCardsFromPlayerHand(ActionList playerOneActions, String playerId, Game game) {
		Integer playerOneIndex = (game.getPlayers()[0].getPlayerId().equals(playerId)) ? 0 : 1;
		Player currentPlayer = game.getPlayers()[playerOneIndex];

		List<Integer> indexesOfCardsThatWerePlayed = new ArrayList<Integer>();
		for(Action action : playerOneActions.getPlayerActions()) {
			if(action instanceof SummonAction) {
				indexesOfCardsThatWerePlayed.add(((SummonAction) action).getFieldCellWhereTheMinionIsBeingSummoned());
			}
		}
		Collections.sort(indexesOfCardsThatWerePlayed);
		for(int i = indexesOfCardsThatWerePlayed.size()-1; i>=0; --i) {
			currentPlayer.removeCardFromHand(i);
		}
	}

	private void resolveAllActions(List<Action> actions, Game game) {
		for (Action action : actions) {
			if (action instanceof SummonAction) {
				resolveSummonAction((SummonAction) action, game);
			} else if (action instanceof AttackAction) {
				resolveAttackAction((AttackAction) action, game);
			}
		}
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

		PlayableMinionCard playableMinionCard = (PlayableMinionCard) playerSummoningTheMinion.getDeck()
				.get(summonAction.getIndexOfCardInHand());
		Minion minion = new Minion(playableMinionCard);
		playerSummoningTheMinion.getField()[fieldCell] = minion;
		System.out.println(playerSummoningTheMinion.getName() + " played " + minion.getName() + " on: " + fieldCell);
		
	}

	private void resolveAttackAction(AttackAction attackAction, Game game) {
		Integer playerDeclaringAttackIndex = attackAction.getPlayerIndex();
		Integer opponentPlayerIndex = (playerDeclaringAttackIndex == PLAYER_ONE_INDEX) ? PLAYER_TWO_INDEX
				: PLAYER_ONE_INDEX;
		Player playerDeclaringAttack = game.getPlayers()[playerDeclaringAttackIndex];
		Player opponentPlayer = game.getPlayers()[opponentPlayerIndex];

		int attackerIndex = attackAction.getAttackingMinionIndex();
		int attackedIndex = attackAction.getTargetIndex();
		int speed = attackAction.getSpeed();
		Minion attacker = playerDeclaringAttack.getField()[attackerIndex];
		verifySpeed(speed, attacker);

		if (attackedIndex == HERO_FACE_INDEX) {
			attackFace(opponentPlayer, attacker);
			return;
		} else {
			attackMinion(opponentPlayer, attackedIndex, attacker);
		}
	}

	private void attackMinion(Player opponentPlayer, int attackedIndex, Minion attacker) {
		Minion targetOfTheAttack;
		targetOfTheAttack = opponentPlayer.getField()[attackedIndex];
		if (targetOfTheAttack != null) {
			attacker.setHealth(attacker.getHealth() - ((Minion) targetOfTheAttack).getPower());
			targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
			if (attacker.isDead()) {
				attacker = null;
			}
			if (targetOfTheAttack.isDead()) {
				targetOfTheAttack = null;
			}
		}
	}

	private void attackFace(Player opponentPlayer, Minion attacker) {
		Hero targetOfTheAttack;
		targetOfTheAttack = opponentPlayer.getHero();
		targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
		if (targetOfTheAttack.isDead()) {
			System.out.println("Congratz grad, you won.");
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
		// Create game
		// Assigner la game aux 2 joueurs qui devraient la recevoir
		Game game = this.gameQueue.matchFirstTwoPlayersInQueue();
		for (Player player : game.getPlayers()) {
			this.newGameList.put(player.getPlayerId(), game);
		}
		this.existingGameList.put(game.getGameId(), game);
	}
}
