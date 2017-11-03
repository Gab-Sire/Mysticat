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
import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.HeroPortrait;
import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.PlayableMinionCard;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.exception.BadCredentialsLoginException;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.exception.UserAlreadyConnectedException;
import com.multitiers.exception.UsernameTakenException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.GameQueue;
import com.multitiers.service.GameService;
import com.multitiers.service.AuthentificationService;
import com.multitiers.util.Constantes;
import com.multitiers.util.JsonUtils;

@RestController
@CrossOrigin
public class RestControlleur {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private MinionCardRepository minionCardRepository;
	
	@Autowired
	private AuthentificationService inscriptionService;
	
	@Autowired
	private GameService gameService;
	
    @GetMapping(value = "/getUserByName/{username}")
    public @ResponseBody User getUserByName(@PathVariable String username) {
    	User user = userRepository.findByUsername(username);
        return user;
    }
    
    @PostMapping(value="/disconnectUser")
    public void disconnectUser(@RequestBody String userId) {
    	inscriptionService.removeUserFromConnectedUsers(userId.substring(0, userId.length()-1));
    }
    
    @PostMapping(value="/getUserDecks")
    public List<Deck> getUserDecks(@RequestBody String userId) {
    	System.out.println("Getting a deck");
    	User user = userRepository.findById(userId.substring(0, userId.length()-1));
    	List<Deck> decks = user.getDecks();
    	return decks;
    }
    
    /**
     * Get One Deck
     */
    @GetMapping(value="/selectOneDeck/{userId}/{deckId}")
    public Deck selectSingleDeck(@PathVariable String userId, @PathVariable int deckId) {
    	User user = userRepository.findById(userId);
    	Deck deck = user.getDecks().get(deckId);
    	Collections.sort(deck.getCardList());
    	return deck;
    }
    
    @PostMapping(value = "/attemptConnection")
    public @ResponseBody String loginWithCredentials(@RequestBody String json, HttpSession session) {
    	UserCredentials userCredentials = JsonUtils.deserializeUserCredentialsFromJson(json);
    	User user = inscriptionService.getUserFromCredentials(userCredentials);
    	if(user != null) {
    		session.setAttribute("userActif", user.getId());
        	inscriptionService.addUserToConnectedUsers(user);
    	}
    	return new Gson().toJson(user.getId());
    }
    
    @PostMapping(value="/signUp")
    public String createUserWithCredentials(@RequestBody String json, HttpSession session) {
    	UserCredentials userCredentials = JsonUtils.deserializeUserCredentialsFromJson(json);
    	
    	String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        if(userRepository.findByUsername(username)!=null) {
    		throw new UsernameTakenException(username);
    	}
    	User user = inscriptionService.createUser(username, password, HeroPortrait.wizardHero);
        userRepository.save(user);
        if(user != null) {
            session.setAttribute("userActif", user.getId());	
        }
    	return new Gson().toJson(user.getId());
    }
    
    @ExceptionHandler(value=UsernameTakenException.class)
    public String handleUsernameTakenSignup(UsernameTakenException e) {
    	return "Le nom d'utilisateur "+ e.username +" est indisponible. ";
    }
    
    @GetMapping(value="/getHardCodedGame")
    public @ResponseBody Game getUserByName() {
        User user1 = userRepository.findByUsername("Chat1");
        User user2 = userRepository.findByUsername("Chat2");
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        Minion minion = new Minion(((PlayableMinionCard)player1.getHand().get(0)));
        player1.addMinion(minion, 0);
        player2.addMinion(minion, 3);
        player1.sendCardToGraveyard(player1.getHand().get(0));
        player1.sendCardToGraveyard(player1.getHand().get(0));
    	Game game = new Game(player1, player2);
        return game;
    }
    
    // TODO use in front end
    @GetMapping(value="/usernameAvailability")
    public Boolean isUsernameAvailable(@RequestBody String jsonUsername) {
    	String username = JsonUtils.deserializeStringFromJson(jsonUsername);
    	return userRepository.findByUsername(username)!=null;
    }
    
    @PostMapping(value="/updateGame")
    public Game updateGame(@RequestBody String  jsonGame) {
    	Game game = JsonUtils.deserializeGameFromJson(jsonGame);
        return game;
    }
    
    @PostMapping(value="/enterQueue")
    public void enterQueue(@RequestBody String userId) {
    	System.out.println(userId);
    	User user = userRepository.findById(userId.substring(0, userId.length()-1));
    	Player player = new Player(user);
    	this.gameService.gameQueue.addToQueue(player);
    }
    
    @PostMapping(value="/cancelQueue")
    public void cancelQueue(@RequestBody String playerId) {
    	Player player = this.gameService.gameQueue.getPlayerInQueueById(playerId.substring(0, playerId.length()-1));
    	if(player!=null) {
    		this.gameService.gameQueue.removeFromQueue(player);
    	}
    }
    
    @PostMapping(value="/checkIfQueuePopped")
    public Game checkIfQueuePopped(@RequestBody String userId) {
    	userId = userId.substring(0, userId.length()-1);
    	if(gameService.newGameList.containsKey(userId)) {
    		return gameService.newGameList.get(userId);
    	}
    	return null;
    }
    
    @GetMapping(value="/getHardCodedActionSample")
    public @ResponseBody ActionList getHardCodedActionSample() {
		ActionList retour = new ActionList();
		retour.setGameId("game1");
		List<Action> actionList = new ArrayList<Action>();
		SummonAction summonAction = new SummonAction();
		summonAction.setFieldCellWhereTheMinionIsBeingSummoned(1);
		summonAction.setIndexOfCardInHand(0);
		summonAction.setPlayerIndex(0);
		
		AttackAction attackAction = new AttackAction();
		attackAction.setAttackingMinionIndex(0);
		attackAction.setPlayerIndex(0);
		attackAction.setSpeed(3);
		attackAction.setTargetIndex(5);
		
		actionList.add(attackAction);
		actionList.add(summonAction);
		
		retour.setPlayerActions(actionList);
		retour.setPlayerId("player1");
		retour.setGameId("game1");
		
    	return retour;
    } 
    
    @PostMapping(value="/sendActions")
    public void sendActions(@RequestBody String actionListJson) {
    	ActionList currentPlayerActionList = JsonUtils.deserializeActionListFromJson(actionListJson);
    	String gameId = currentPlayerActionList.getGameId();
    	if(this.gameService.sentActionLists.containsKey(gameId)){
    		ActionList otherPlayerAction = this.gameService.sentActionLists.get(gameId);
    		this.gameService.calculateNextTurnFromActionLists(otherPlayerAction, currentPlayerActionList);
    	}
    	else {
    		this.gameService.sentActionLists.put(gameId, currentPlayerActionList);
    	}
    }
    
    @PostMapping(value="/checkIfGameUpdated")
    public @ResponseBody Game getUpdatedGame(@RequestBody String userId) {
    	userId = userId.substring(0, userId.length()-1);
    	Game game = this.gameService.updatedGameList.get(userId);
    	if(game!=null) {
    		this.gameService.updatedGameList.remove(userId);
    	}
    	return game;
    }
    
    @GetMapping(value="/getServerStatus")
    public void getServerStatus() {
    }
    
    @ExceptionHandler(value=BadCredentialsLoginException.class)
    public String handleBadCredentialsLogin() {
    	return "Le nom d'utilisateur et le mot de passe que vous avez entre ne correspondent pas.";
    }
    
    @ExceptionHandler(value=BadPasswordFormatException.class)
    public String handleBadPasswordSignup() {
    	return "Votre mot de passe est dans un format invalide.\n"+
				"<h2>Le mot de passe doit comprendre:</h2> \n"+
				"<ul><li>Entre "+Constantes.MIN_PASSWORD_LENGTH+" et "+Constantes.MAX_PASSWORD_LENGTH+" caracteres inclusivement</li>"+
				"<li>Au moins 1 lettre minuscle</li>"+
				"<li>Au moins 1 lettre majuscule</li>"+
				"<li>Au moins un chiffre</li></ul>";
    }
    @ExceptionHandler(value=BadUsernameFormatException.class)
    public String handleBadUsernameSignup() {
    	return "Votre mot de passe est dans un format invalide.\n"+
				"<h2>Le nom d'utilisateur doit comprendre:</h2> \n"+
				"<ul><li>Entre "+Constantes.MIN_USERNAME_LENGTH+" et "+Constantes.MAX_USERNAME_LENGTH+" caracteres inclusivement</li>"
				+ "<li>Au moins 1 chiffre</li>"
				+ "<li>Au moins 1 lettre minuscule</li>"
				+ "<li>Au moins 1 lettre majuscule</li></ul>";
    }
}
