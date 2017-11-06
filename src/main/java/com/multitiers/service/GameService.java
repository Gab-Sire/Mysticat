package com.multitiers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Hero;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.PlayableCard;
import com.multitiers.domaine.ingame.PlayableMinionCard;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.domaine.ingame.SurrenderAction;
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
		if (playerOneId.equals(playerTwoId)) {
			throw new RuntimeException("Duplicate action submission.");
		}
		List<Action> actions = getCompleteSortedActionList(playerOneActions, playerTwoActions);
		Game game = this.existingGameList.get(gameId);

		resolveAllActions(actions, game);

		removePlayedCardsFromPlayerHand(playerOneActions, playerOneId, game);
		removePlayedCardsFromPlayerHand(playerTwoActions, playerTwoId, game);
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

	private void resolveAllActions(List<Action> actions, Game game) {
		for (Action action : actions) {
			if (action instanceof SummonAction && !game.getEndedWithSurrender()) {
				resolveSummonAction((SummonAction) action, game);
			} else if (action instanceof AttackAction && !game.getEndedWithSurrender()) {
				resolveAttackAction((AttackAction) action, game);
			} else if (action instanceof SurrenderAction) {
				game.setEndedWithSurrender(true);
				resolveSurrenderAction((SurrenderAction) action, game);
			}
			sendDeadMinionsToGraveyards(game);
		}
	}
	
	// Enleve seulement les minions mort en ce moment.
	private void sendDeadMinionsToGraveyards(Game game) {
		Player[] players = game.getPlayers();
		for(Player player : players) {
			Minion[] field = player.getField();
			List<PlayableCard> graveyard = player.getGraveyard();
			for(int i=0; i<field.length; ++i) {
				Minion minion = field[i];
				if(minion!=null) {
					if(minion.isDead()) {
						field[i] = null;
						graveyard.add(minion.getCardReference());
					}
				}
			}
			player.setGraveyard(graveyard);
			player.setField(field);
		}
	}
	
	private List<Action> getCompleteSortedActionList(ActionList playerOneActions, ActionList playerTwoActions) {
		List<Action> actions = new ArrayList<>();
		actions.addAll(playerOneActions.getPlayerActions());
		actions.addAll(playerTwoActions.getPlayerActions());
		Collections.sort(actions);
		return actions;
	}

	private void resolveSummonAction(SummonAction summonAction, Game game) {
		Player playerSummoningTheMinion = game.getPlayers()[summonAction.getPlayerIndex()];
		int fieldCell = summonAction.getFieldCellWhereTheMinionIsBeingSummoned();

		PlayableMinionCard playableMinionCard = (PlayableMinionCard) playerSummoningTheMinion.getHand()
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
		if (attacker == null) {
			System.out.println("The minion on " + attackerIndex + " for " + playerDeclaringAttack.getName()
					+ " had previously declared an attack but was killed before it could resolve");
		} 
		else {
			verifySpeed(speed, attacker);
			if (attackedIndex == HERO_FACE_INDEX) {
				attackFace(opponentPlayer, attacker);
				if(opponentPlayer.getHero().isDead() && game.getWinnerPlayerIndex() == null) {
					game.setWinnerPlayerIndex(playerDeclaringAttackIndex);
				}
			} else {
				attackMinion(opponentPlayer, attackedIndex, attacker);
			}
		}
		if(game.getWinnerPlayerIndex()!=null) {
			existingGameList.remove(game.getGameId());
		}
	}
	
	private void resolveSurrenderAction(SurrenderAction surrenderAction, Game game) {
		Integer playerDeclaringSurrenderIndex = surrenderAction.getPlayerIndex();
		Integer opponentPlayerIndex = (playerDeclaringSurrenderIndex == PLAYER_ONE_INDEX) ? PLAYER_TWO_INDEX
				: PLAYER_ONE_INDEX;
		Player playerDeclaringSurrender = game.getPlayers()[playerDeclaringSurrenderIndex];
		Player opponentPlayer = game.getPlayers()[opponentPlayerIndex];
		
		playerDeclaringSurrender.getHero().setHealth(0);
		if(game.getEndedWithSurrender()) {
			game.setWinnerPlayerIndex(-1);
		}
		else {
			game.setWinnerPlayerIndex(opponentPlayerIndex);
		}
	}

	private void attackMinion(Player opponentPlayer, int attackedIndex, Minion attacker) {
		Minion targetOfTheAttack;
		targetOfTheAttack = opponentPlayer.getField()[attackedIndex];
		if (targetOfTheAttack != null) {
			attacker.setHealth(attacker.getHealth() - ((Minion) targetOfTheAttack).getPower());
			targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
			if (attacker.isDead()) {
				System.out.println(attacker.getName() + " attacked " + targetOfTheAttack.getName() + " and died.");
			}
			if (targetOfTheAttack.isDead()) {
				System.out.println("Target on cell: " + attackedIndex + " for " + opponentPlayer.getName()
						+ " was killed by " + attacker.getName());
			}
		} else {
			System.out.println("Target is missing or dead.");
		}

	}

	private void attackFace(Player opponentPlayer, Minion attacker) {
		Hero targetOfTheAttack;
		targetOfTheAttack = opponentPlayer.getHero();
		targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
		if (targetOfTheAttack.isDead()) {
			//System.out.println("Congratz grad, you won.");
		}
	}

	private void verifySpeed(int speed, Minion attacker) {
		if (attacker.getSpeed() != speed) {
			// TODO Gestion du mismatch
			System.out.println("Error: Mismatch in speed for minion " + attacker.getName());
		}
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
