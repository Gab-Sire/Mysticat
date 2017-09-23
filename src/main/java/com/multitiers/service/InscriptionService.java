package com.multitiers.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.ProjetMultitiersApplication;
import com.multitiers.domaine.Card;
import com.multitiers.domaine.Deck;
import com.multitiers.domaine.User;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;

@Service
public class InscriptionService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ProjetMultitiersApplication.class);
    
    @Transactional
    public static User createUser(String username, String password) {
    	String salt = ConnectionUtils.generateSalt();
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
    	User user = new User(username, hashedPassword, salt);
    	user.setId(ConnectionUtils.generateUUID().toString());
    	assignStarterDeck(user);
        return user;
    }

    @Transactional
    public void peuplement() {
    	//Methode qu'on va utiliser pour Bootstrapper
        User user1 = createUser("Chat1", "myboy");
        User user2 = createUser("Chat2", "myboy");
        userRepository.save(user1);
        userRepository.save(user2);
    }
    
	private static void assignStarterDeck(User user) {
		Set<Deck> decks = new HashSet<Deck>();
    	decks.add(createStarterDeck(user));
    	user.setDecks(decks);
	}
    
    public static Deck createStarterDeck(User owner) {
    	Deck starterDeck = new Deck();
    	starterDeck.setDeckId(ConnectionUtils.generateUUID().toString());
    	List<Card> defaultCards = new ArrayList<Card>();
    	starterDeck.setCardList(defaultCards);
    	return starterDeck;
    }
}
