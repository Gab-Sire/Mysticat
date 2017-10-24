package com.multitiers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.HeroPortrait;
import com.multitiers.domaine.entity.MinionCard;
import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.exception.BadCredentialsLoginException;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.exception.UserAlreadyConnectedException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@Service
public class SubscriptionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private MinionCardRepository minionCardRepository;
    
    //Key: userId
    public Map<String, User> connectedUsers;
    
    public SubscriptionService() {}
    
    @Transactional
    public void bootStrapTwoUsersAndTestCardSet() {
    	insertCustomCardsInDatabase();
    	//Methode qu'on va utiliser pour Bootstrapper
        for(int i=1; i<=Constantes.NB_OF_CARDS_IN_TEST_SET; ++i) {
        	Integer stats = (((i/5)<=0)) ? 1 : i/5;
        	int manaCost = 1;
            MinionCard card = createMinionCard("Minion"+i, stats, stats, stats, manaCost, stats+" mana"+" "+stats+"/"+stats);
            cardRepository.save(card);
        }
        
        User user1 = createUser("Chat1", "Myboy1", HeroPortrait.warriorHero);
        User user2 = createUser("Chat2", "Myboy2", HeroPortrait.zorroHero);
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
    
    public  User createUser(String username, String password, HeroPortrait portrait) {
    	if(!ConnectionUtils.isValidPassword(password)) {
    		throw new BadPasswordFormatException(password);
    	}
    	else if(!ConnectionUtils.isValidUsername(username)) {
    		throw new BadUsernameFormatException(username);
    	}
    	
    	String salt = ConnectionUtils.generateSalt();
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
    	User user = new User(username, hashedPassword, salt);
    	user.setHeroPortrait(portrait);
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
    	
    	List<Card> cards = cardRepository.findAll();
    	Collections.shuffle(cards);
    	cards = cards.subList(0, Constantes.CONSTRUCTED_DECK_MAX_SIZE);
    	
    	starterDeck.setCardList(cards);
    	return starterDeck;
    }
    
    public User getUserFromCredentials(UserCredentials userCredentials) {
    	String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
    	User user = userRepository.findByUsername(username);
    	if(user==null) {
    		throw new BadCredentialsLoginException();
    	}
        String hashedSalt = user.getHashedSalt();
	     if(ConnectionUtils.hashPassword(password, hashedSalt).equals(user.getPasswordHash())) {
	    	 return user;
	     }
	     throw new BadCredentialsLoginException();
    }
    
    public void addUserToConnectedUsers(User user) {
    	if(this.connectedUsers==null) {
    		this.connectedUsers = new HashMap<String, User>();
    	}
    	if (this.connectedUsers.containsKey(user.getId())) {
        	throw new UserAlreadyConnectedException();
        }
    	else {
    		this.connectedUsers.put(user.getId(), user);
    	}
    }
    
    public void insertCustomCardsInDatabase() {
    	
    	// Cartes de Gabriel, theme Halloween
    	MinionCard minionCard01 = createMinionCard("Chat Momie", 3, 4, 3, 1, "La mal�diction du pharaon");
    	MinionCard minionCard02 = createMinionCard("Chat-Souris", 3, 3, 4, 1, "Vous avez dit chat-souris?");
    	MinionCard minionCard03 = createMinionCard("Chat Fantome", 2, 6, 2, 1, "Boo");
    	MinionCard minionCard04 = createMinionCard("Chat Noir", 2, 4, 4, 1, "Si c'est vendredi 13, bonne chance pour la suite");
    	MinionCard minionCard05 = createMinionCard("Jack-O-Chat", 4, 6, 5, 2, "Bonne carte sans l'ombre d'un doute");
    	MinionCard minionCard06 = createMinionCard("Apprenti-Sorcier", 5, 6, 4, 2, "Abra Kadrachat !");
    	MinionCard minionCard07 = createMinionCard("Chat Zombie", 6, 4, 5, 2, "OMFG BBQ");
    	MinionCard minionCard08 = createMinionCard("FrankenChat", 7, 8, 5, 3, "Combien de vies de chats en �change de cette cr�ature ?");
    	MinionCard minionCard09 = createMinionCard("Chat Poss�d�", 9, 6, 5, 3, "Ehhh boy");
    	MinionCard minionCard10 = createMinionCard("Chanatique", 6, 8, 6, 3, "Tellement myst�rieux...");
    	
    	
    	//Carte des Marc-Antoine, theme Super Hero et Super Vilain
    	MinionCard minionCard11 = createMinionCard("MechaChat", 10, 30, 5, 8, "Technologie et mauvaises intentions.");
    	MinionCard minionCard12 = createMinionCard("DoppleMeower", 10, 1, 19, 5, "Une vision terrible.");
    	MinionCard minionCard13 = createMinionCard("Moustache Rousse", 5, 10, 5, 3, "Le pirate le plus dangereux. Il a quand même peur de l'eau.");
    	MinionCard minionCard14 = createMinionCard("Channibal Lecter", 10, 15, 15, 7, "Un chat qui mange d'autres chats.");
    	MinionCard minionCard15 = createMinionCard("Ashes, The Purrifier", 25, 30, 0, 10, "Démon des temps anciens qui souhaite dominer le monde.");
    	MinionCard minionCard16 = createMinionCard("Captain AmeriChat", 10, 20, 10, 7, "Protège la nation.");
    	MinionCard minionCard17 = createMinionCard("Super Chat", 15, 25, 5, 8, "Un chat super.");
    	MinionCard minionCard18 = createMinionCard("Golden Claws", 20, 5, 5, 5, "Ses griffes légendaires peuvent couper n'importe quoi.");
    	MinionCard minionCard19 = createMinionCard("Red Dot Catcher", 5, 10, 30, 8, "Plus rapide que le point rouge par terre.");
    	MinionCard minionCard20 = createMinionCard("The Incredible Whisker", 5, 10, 10, 4, "Son elegance est incomparable.");
    	
    	
    	cardRepository.save(minionCard01);	cardRepository.save(minionCard02);	cardRepository.save(minionCard03);
    	cardRepository.save(minionCard04);	cardRepository.save(minionCard05);	cardRepository.save(minionCard06);
    	cardRepository.save(minionCard07);	cardRepository.save(minionCard08);	cardRepository.save(minionCard09);
    	cardRepository.save(minionCard10);
    	
    	cardRepository.save(minionCard11);	cardRepository.save(minionCard12);	cardRepository.save(minionCard13);
    	cardRepository.save(minionCard14);	cardRepository.save(minionCard15);	cardRepository.save(minionCard16);
    	cardRepository.save(minionCard17);	cardRepository.save(minionCard18);	cardRepository.save(minionCard19);
    	cardRepository.save(minionCard20);
    }

    public void removeUserFromConnectedUsers(String userId) {
    	this.connectedUsers.remove(userId);
    }
    
    public void initDataLists() {
        this.connectedUsers = new HashMap<String, User>();
    }
    
}
