package com.multitiers.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.HeroPortrait;
import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.domaine.entity.UserDeck;
import com.multitiers.domaine.entity.UserStatus;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.PlayableMinionCard;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SurrenderAction;
import com.multitiers.exception.BadCredentialsLoginException;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.exception.UsernameTakenException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.AuthentificationService;
import com.multitiers.service.DeckEditingService;
import com.multitiers.service.GameService;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;
import com.multitiers.util.JsonUtils;

@RestController
@CrossOrigin
public class RestControlleur {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private AuthentificationService authService;
	@Autowired
	private GameService gameService;
	@Autowired
	private DeckEditingService deckEditingService;

	@GetMapping(value = "/getUserByName/{username}")
	public @ResponseBody User getUserByName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	@PostMapping(value = "/disconnectUser")
	public void disconnectUser(@RequestBody String userId) {
		String actualUserId = userId.substring(0, userId.length() - 1);
		try {
			Player player = this.gameService.gameQueue.getPlayerInQueueById(actualUserId);
			gameService.gameQueue.removeFromQueue(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		authService.removeUserFromConnectedUsers(actualUserId);
	}

	@PostMapping(value = "/getPlayerConnection")
	public Boolean getPlayerConnection(@RequestBody String userId) {
		if (null != authService.getConnectedUsers()) {
			return authService.isThisUserConnected(userId.substring(0, userId.length() - 1));
		}
		return false;
	}

	@PostMapping(value = "/getAllUsers")
	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@PostMapping(value = "/getAllUserStatus")
	public List<UserStatus> getAllUserStatus() {
		List<User> users = userRepository.findAll();
		List<UserStatus> userStatuses = new ArrayList<>();
		for (User user : users) {
			UserStatus status = new UserStatus(user);
			status.setIsConnected(authService.isThisUserConnected(user.getId()));
			userStatuses.add(status);
		}
		Collections.sort(userStatuses);
		return userStatuses;
	}

	@PostMapping(value = "/getUserDecks")
	public List<Deck> getUserDecks(@RequestBody String userId) {
		userId = userId.substring(0, userId.length() - 1);
		if (!authService.isThisUserConnected(userId)) {
			throw new RuntimeException("User no longer connected.");
		}
		User user = userRepository.findById(userId);
		List<Deck> decks = user.getDecks();
		return decks;
	}

	@GetMapping(value = "/selectOneDeck/{userId}/{deckId}")
	public Deck selectSingleDeck(@PathVariable String userId, @PathVariable int deckId) {
		if (!authService.isThisUserConnected(userId)) {
			throw new RuntimeException("User no longer connected.");
		}
		User user = userRepository.findById(userId);
		if (deckId >= user.getDecks().size()) {
			Deck newDeck = new Deck();
			newDeck.setDeckId(ConnectionUtils.generateUUID().toString());
			newDeck.setName("New Deck");
			newDeck.setCardList(new ArrayList<Card>());
			return newDeck;
		}
		Deck deck = user.getDecks().get(deckId);
		Collections.sort(deck.getCardList());
		return deck;
	}
	
	@GetMapping(value="/getFavoriteDeckIndex/{userId}")
	public Integer getFavoriteDeckIndex(@PathVariable String userId) {
		User user = userRepository.findById(userId);
		if(user==null) {
			throw new RuntimeException("User is null.");
		}
		return user.getFavoriteDeck();
	}

	@PostMapping(value = "/getCollection")
	public List<Card> getCollection() {
		List<Card> cardCollection = cardRepository.findAll();
		Collections.sort(cardCollection);
		return cardCollection;
	}

	@PostMapping(value = "/saveDeck")
	public void saveDeck(@RequestBody String json) {
		UserDeck userDeck = JsonUtils.deserializeUserDeckFromJson(json);
		String userId = userDeck.getUserId();
		if (!authService.isThisUserConnected(userId)) {
			throw new RuntimeException("User no longer connected.");
		}
		User user = userRepository.findById(userId);

		if (user == null) {
			System.out.println("Null user cannot save deck.");
			return;
		}

		List<String> cardIds = userDeck.getCardIds();
		Integer deckIndex = userDeck.getDeckIndex();
		String deckName = userDeck.getDeckName();

		List<Card> cardList = new ArrayList<>();

		for (String cardId : cardIds) {
			cardList.add(cardRepository.findByCardId(cardId));
		}

		Deck newDeck = new Deck();
		newDeck.setDeckId(ConnectionUtils.generateUUID().toString());
		newDeck.setName(deckName);
		newDeck.setCardList(cardList);
		deckEditingService.changeDeck(user, deckIndex, newDeck);
		System.out.println("Saved deck");
	}

	@PostMapping(value = "/attemptConnection")
	public @ResponseBody String loginWithCredentials(@RequestBody String json, HttpSession session) {
		UserCredentials userCredentials = JsonUtils.deserializeUserCredentialsFromJson(json);
		User user = authService.getUserFromCredentials(userCredentials);
		if (user != null) {
			session.setAttribute("userActif", user.getId());
			authService.addUserToConnectedUsers(user);
		}
		return new Gson().toJson(user.getId());
	}

	@PostMapping(value = "/signUp")
	public String createUserWithCredentials(@RequestBody String json, HttpSession session) {
		UserCredentials userCredentials = JsonUtils.deserializeUserCredentialsFromJson(json);

		String username = userCredentials.getUsername();
		String password = userCredentials.getPassword();
		if (userRepository.findByUsername(username) != null) {
			throw new UsernameTakenException(username);
		}
		User user = authService.createUser(username, password, HeroPortrait.wizardHero);
		userRepository.save(user);
		if (user != null) {
			session.setAttribute("userActif", user.getId());
			authService.addUserToConnectedUsers(user);
		}
		return new Gson().toJson(user.getId());
	}

	// TODO use in front end
	@GetMapping(value = "/usernameAvailability")
	public Boolean isUsernameAvailable(@RequestBody String jsonUsername) {
		String username = JsonUtils.deserializeStringFromJson(jsonUsername);
		return userRepository.findByUsername(username) != null;
	}

	@PostMapping(value = "/enterQueue")
	public void enterQueue(@RequestBody String userId) {
		userId = userId.substring(0, userId.length() - 1);
		if (!authService.isThisUserConnected(userId)) {
			throw new RuntimeException("User no longer connected.");
		}
		User user = userRepository.findById(userId);
		Player player = new Player(user);
		this.gameService.gameQueue.addToQueue(player);
	}

	@PostMapping(value = "/cancelQueue")
	public void cancelQueue(@RequestBody String playerId) {
		playerId = playerId.substring(0, playerId.length() - 1);
		Player player = this.gameService.gameQueue.getPlayerInQueueById(playerId);
		if (player != null) {
			this.gameService.gameQueue.removeFromQueue(player);
		}
		if (!authService.isThisUserConnected(playerId)) {
			throw new RuntimeException("User no longer connected.");
		}
	}

	@PostMapping(value = "/checkIfQueuePopped")
	public Game checkIfQueuePopped(@RequestBody String userId) {
		userId = userId.substring(0, userId.length() - 1);
		if (!authService.isThisUserConnected(userId)) {
			throw new RuntimeException("User no longer connected.");
		}
		if (gameService.newGameList.containsKey(userId)) {
			return gameService.newGameList.get(userId);
		}
		return null;
	}

	@PostMapping(value = "/sendActions")
	public void sendActions(@RequestBody String actionListJson) {
		ActionList currentPlayerActionList = JsonUtils.deserializeActionListFromJson(actionListJson);
		String gameId = currentPlayerActionList.getGameId();
		gameService.newGameList.remove(currentPlayerActionList.getPlayerId());
		gameService.updatedGameList.remove(currentPlayerActionList.getPlayerId());
		ActionList otherPlayerAction = this.gameService.sentActionLists.get(gameId);
		if (this.gameService.sentActionLists.containsKey(gameId)
				&& !currentPlayerActionList.isSamePlayerSurrendering(otherPlayerAction)) {
			String playerOneId = currentPlayerActionList.getPlayerId();
			String playerTwoId = otherPlayerAction.getPlayerId();

			if (playerTwoId.equals(playerOneId)) {
				throw new RuntimeException("Duplicate action submission.");
			}
			if (!authService.getConnectedUsers().containsKey(playerOneId)) {
				gameService.replaceKickedPlayerActionsWithSurrender(currentPlayerActionList);
			}
			if (!authService.getConnectedUsers().containsKey(playerTwoId)) {
				gameService.replaceKickedPlayerActionsWithSurrender(otherPlayerAction);
			}
			this.gameService.calculateNextTurnFromActionLists(otherPlayerAction, currentPlayerActionList);
		} else {
			this.gameService.sentActionLists.put(gameId, currentPlayerActionList);
		}
	}

	@PostMapping(value = "/checkIfGameUpdated")
	public @ResponseBody Game getUpdatedGame(@RequestBody String userId) {
		userId = userId.substring(0, userId.length() - 1);
		Game game = this.gameService.updatedGameList.get(userId);
		if (game != null) {
			this.gameService.updatedGameList.remove(userId);
		}
		return game;
	}

	@GetMapping(value = "/getServerStatus")
	public void getServerStatus() {
	}

	@GetMapping(value = "/getHardCodedGame")
	public @ResponseBody Game getUserByName() {
		User user1 = userRepository.findByUsername("Chat1");
		User user2 = userRepository.findByUsername("Chat2");
		Player player1 = new Player(user1);
		Player player2 = new Player(user2);
		Minion minion = new Minion(((PlayableMinionCard) player1.getHand().get(0)));
		player1.addMinion(minion, 0);
		player2.addMinion(minion, 3);
		player1.sendCardToGraveyard(player1.getHand().get(0));
		player1.sendCardToGraveyard(player1.getHand().get(0));
		Game game = new Game(player1, player2);
		return game;
	}

	@ExceptionHandler(value = BadCredentialsLoginException.class)
	public String handleBadCredentialsLogin() {
		return "Le nom d'utilisateur et le mot de passe que vous avez entre ne correspondent pas.";
	}

	@ExceptionHandler(value = UsernameTakenException.class)
	public String handleUsernameTakenSignup(UsernameTakenException e) {
		return "Le nom d'utilisateur " + e.username + " est indisponible. ";
	}

	@ExceptionHandler(value = BadPasswordFormatException.class)
	public String handleBadPasswordSignup() {
		return "Votre mot de passe est dans un format invalide.\n" + "<h2>Le mot de passe doit comprendre:</h2> \n"
				+ "<ul><li>Entre " + Constantes.MIN_PASSWORD_LENGTH + " et " + Constantes.MAX_PASSWORD_LENGTH
				+ " caracteres inclusivement</li>" + "<li>Au moins 1 lettre minuscle</li>"
				+ "<li>Au moins 1 lettre majuscule</li>" + "<li>Au moins un chiffre</li></ul>";
	}

	@ExceptionHandler(value = BadUsernameFormatException.class)
	public String handleBadUsernameSignup() {
		return "Votre mot de passe est dans un format invalide.\n" + "<h2>Le nom d'utilisateur doit comprendre:</h2> \n"
				+ "<ul><li>Entre " + Constantes.MIN_USERNAME_LENGTH + " et " + Constantes.MAX_USERNAME_LENGTH
				+ " caracteres inclusivement</li>" + "<li>Au moins 1 chiffre</li>"
				+ "<li>Au moins 1 lettre minuscule</li>" + "<li>Au moins 1 lettre majuscule</li></ul>";
	}
}
