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
import com.multitiers.domaine.MinionCard;
import com.multitiers.domaine.User;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@Service
public class InscriptionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeckRepository deckRepository;
    private static final Logger log = LoggerFactory.getLogger(ProjetMultitiersApplication.class);
    
    @Transactional
    public void inscription(String username, String password) {
    	String salt = ConnectionUtils.generateSalt();
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
    	User user = new User(username, hashedPassword, salt);
    	user.setId(ConnectionUtils.generateUUID().toString());
    	Set<Deck> decks = new HashSet<Deck>();
    	decks.add(createStarterDeck(user));
    	user.setDecks(decks);
        userRepository.save(user);
    }
    
    @Transactional
    public void peuplement() {
    	String salt = "salt";
    	String password = "myboy";
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
        userRepository.save(new User("Chat1", hashedPassword, salt));
    }
    
    public Deck createStarterDeck(User owner) {
    	Deck starterDeck = new Deck();
    	starterDeck.setDeckId(ConnectionUtils.generateUUID().toString());
    	starterDeck.setOwner(owner);
    	List<Card> defaultCards = new ArrayList<Card>();
    	starterDeck.setCardList(defaultCards);
    	return starterDeck;
    }
}
