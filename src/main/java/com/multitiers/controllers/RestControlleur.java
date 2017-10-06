package com.multitiers.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.PlayableMinionCard;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.exception.BadCredentialsLoginException;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.exception.UsernameTakenException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.GameService;
import com.multitiers.service.InscriptionService;
import com.multitiers.util.Constantes;

@RestController
@CrossOrigin
public class RestControlleur {
	private static final int PLAYER_TWO_INDEX = 1;
	private static final int PLAYER_ONE_INDEX = 0;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private MinionCardRepository minionCardRepository;
	
	@Autowired
	private InscriptionService inscriptionService;
	
	@Autowired
	private GameService gameService;
	
    @GetMapping(value = "/getUserByName/{username}")
    public @ResponseBody User getUserByName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    
    @PostMapping(value = "/attemptConnection")
    public @ResponseBody User loginWithCredentials(@ModelAttribute UserCredentials userCredentials, HttpSession session) {
    	User user = inscriptionService.getUserFromCredentials(userCredentials);
    	session.setAttribute("userActif", user.getId());
    	return user;
    }

    /*
    @PostMapping(value = "/Connection")
    public @ResponseBody Boolean ConnectionPost(@ModelAttribute UserCredentials userCredentials) {
        boolean authentication = attemptConnectionPost(userCredentials);
        if(authentication) {
        	String username = userCredentials.getUsername();
        	user = userRepository.findByUsername(username);
        }
        return authentication;
    }
    */
    
    @PostMapping(value="/signUp")
    public User createUserWithCredentials(@ModelAttribute UserCredentials userCredentials, HttpSession session) {
    	String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
    	if(userRepository.findByUsername(username)!=null) {
    		throw new UsernameTakenException(username);
    	}
    	User user = inscriptionService.createUser(username, password);
        userRepository.save(user);
        session.setAttribute("userActif", user.getId());
    	return user;
    }
    
    //TODO EQ1-49 Instancier une partie a partir du user qui la demande.
    //Deuxieme user sera hardcoded pour tester 
    //Demande les credentials, car on n'a pas de session
    //Fonction test seulement, pas besoin de checker pour des exceptions
    @PostMapping(value="/enterGame")
    @ResponseBody
    public Game enterGame(@ModelAttribute UserCredentials userCredentials) {
    	User user = inscriptionService.getUserFromCredentials(userCredentials);
    	User userHardCoded = userRepository.findByUsername("Chat2");
    	Player player1 = new Player(user);
    	Player player2 = new Player(userHardCoded);
        player1.setPlayerIndex(PLAYER_ONE_INDEX);
        player2.setPlayerIndex(PLAYER_TWO_INDEX);
    	Game game = new Game(player1, player2);
    	return game;
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
        player1.setPlayerIndex(PLAYER_ONE_INDEX);
        player2.setPlayerIndex(PLAYER_TWO_INDEX);
        Minion minion01 = new Minion((PlayableMinionCard) player1.getDeck().get(0));
        player1.addMinion(minion01, 0);
        
    	Game game = new Game(player1, player2);
        return game;
    }
    
    
    @PostMapping(value="updateGame")
    public Game updateGame(@RequestBody String  jsonGame) {
    	Game game = gameService.deserializeGame(jsonGame);
        return game;
    }
}
