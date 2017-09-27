package com.multitiers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.multitiers.domaine.User;
import com.multitiers.domaine.UserCredentials;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.Game;
import com.multitiers.service.InscriptionService;
import com.multitiers.service.Player;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@RestController
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
	private InscriptionService inscriptionService;
	
    @GetMapping(value = "/getUserByName/{username}")
    public @ResponseBody User getUserByName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    
    @PostMapping(value = "/attemptConnection")
    public @ResponseBody Boolean attemptConnectionPost(@ModelAttribute UserCredentials userCredentials) {
    	return inscriptionService.getUserFromCredentials(userCredentials)!=null;
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
    public User userSubmit(@ModelAttribute UserCredentials userCredentials) {
    	String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
    	User user = inscriptionService.createUser(username, password);
        userRepository.save(user);
    	return user;
    }
    
    //TODO EQ1-49 Instancier une partie a partir du user qui la demande.
    //Deuxieme user sera hardcoded pour tester 
    //Demande les credentials, car on n'a pas de session
    @PostMapping(value="/enterGame")
    @ResponseBody
    public Game enterGame(@ModelAttribute UserCredentials userCredentials) {
    	User user = inscriptionService.getUserFromCredentials(userCredentials);
    	User userHardCoded = userRepository.findByUsername("Chat2");
    	Player player1 = new Player(user);
    	Player player2 = new Player(userHardCoded);
    	Game game = new Game(player1, player2);
    	return game;
    }
    
    //Fonction qui est lancee lorsqu'une erreur survient.
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
				"<ul><li>Entre "+Constantes.MIN_USERNAME_LENGTH+" et "+Constantes.MAX_USERNAME_LENGTH+" caracteres inclusivement</li>";
    }
}
