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
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.InscriptionService;
import com.multitiers.util.ConnectionUtils;

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
    	String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
    	User user = userRepository.findByUsername(username);
        String hashedSalt = user.getHashedSalt();
        return ConnectionUtils.hashPassword(password, hashedSalt).equals(user.getPasswordHash());
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
    
    //Fonction qui est lancee lorsqu'une erreur survient.
    @ExceptionHandler(value=Exception.class)
    public String errorMessage() {
    	return "Erreur";
    }

}
