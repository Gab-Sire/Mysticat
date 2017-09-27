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
import com.multitiers.domaine.UserCredentials;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@Service
public class InscriptionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private MinionCardRepository minionCardRepository;
    private static final Logger log = LoggerFactory.getLogger(ProjetMultitiersApplication.class);
        
    public InscriptionService() {}
    
    @Transactional
    public void peuplement() {
    	//Methode qu'on va utiliser pour Bootstrapper
        for(int i=1; i<=Constantes.NB_OF_CARDS_IN_TEST_SET; i++) {
            MinionCard card = createMinionCard("MinionCard"+i, i, i, i, i, i+" mana"+" "+i+"/"+i);
            cardRepository.save(card);
        }
        
        User user1 = createUser("Chat1", "Myboy1");
        User user2 = createUser("Chat2", "Myboy2");
        userRepository.save(user1);
        userRepository.save(user2);
    }
    
    public  MinionCard createMinionCard(String name, Integer power, Integer health, Integer speed, Integer manaCost, String desc) {
    	MinionCard card = new MinionCard();
    	card.setCardId(ConnectionUtils.generateUUID().toString());
    	card.setCardName(name);
    	card.setInitialHealth(health);
    	card.setInitialPower(power);
    	card.setInitialSpeed(speed);
    	card.setManaCost(manaCost);
    	card.setCardDescription(desc);
    	return card;
    }
    
    public  User createUser(String username, String password) {
    	if(!ConnectionUtils.isValidPassword(password)) {
    		throw new BadPasswordFormatException(password);
    	}
    	else if(!ConnectionUtils.isValidUsername(username)) {
    		throw new BadUsernameFormatException(username);
    	}
    	
    	String salt = ConnectionUtils.generateSalt();
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
    	User user = new User(username, hashedPassword, salt);
    	user.setId(ConnectionUtils.generateUUID().toString());
    	assignStarterDeck(user);
        return user;
    }
    
	private  void assignStarterDeck(User user) {
		List<Deck> decks = new ArrayList<Deck>();
    	decks.add(createStarterDeck(user));
    	user.setDecks(decks);
	}
    
    public Deck createStarterDeck(User owner) {
    	Deck starterDeck = new Deck();
    	starterDeck.setDeckId(ConnectionUtils.generateUUID().toString());
    	List<Card> defaultCards = new ArrayList<Card>();
    	
    	for(int i=1; i<=Constantes.CONSTRUCTED_DECK_MAX_SIZE; i++) {
    		Integer cardIndex = (int) (Math.random()*Constantes.NB_OF_CARDS_IN_TEST_SET)+1;
    		Card cardToAdd = cardRepository.findByCardName("MinionCard"+cardIndex);
    		defaultCards.add(cardToAdd);
    	}
    	
    	starterDeck.setCardList(defaultCards);
    	return starterDeck;
    }
    
    public User getUserFromCredentials(UserCredentials userCredentials) {
    	String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
    	User user = userRepository.findByUsername(username);
        String hashedSalt = user.getHashedSalt();
         if(ConnectionUtils.hashPassword(password, hashedSalt).equals(user.getPasswordHash())) {
        	 return user;
         }
         return null;
    }
    
}
