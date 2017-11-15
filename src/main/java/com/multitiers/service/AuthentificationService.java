
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
import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.exception.BadCredentialsLoginException;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.exception.UserAlreadyConnectedException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@Service
public class AuthentificationService {

	public static final String ADMIN_NAME = "Admin";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CardRepository cardRepository;

	// Key: userId
	private Map<String, User> connectedUsers;

	public Map<String, User> getConnectedUsers() {
		return connectedUsers;
	}

	public void setConnectedUsers(Map<String, User> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}

	@Transactional
	public void bootStrapTwoUsers() {
		User user1 = createUser("Chat1", "Myboy1", HeroPortrait.warriorHero);
		User user2 = createUser("Chat2", "Myboy2", HeroPortrait.zorroHero);
		User admin = createUser("Admin", "Toor1", HeroPortrait.zorroHero);
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(admin);
	}

	public User createUser(String username, String password, HeroPortrait portrait) {
		if (!ConnectionUtils.isValidPassword(password)) {
			throw new BadPasswordFormatException(password);
		} else if (!ConnectionUtils.isValidUsername(username)) {
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

	private void assignStarterDeck(User user) {
		List<Deck> decks = new ArrayList<Deck>();
		decks.add(createStarterDeck(user));
		user.setDecks(decks);
	}

	public Deck createStarterDeck(User owner) {
		Deck starterDeck = new Deck();
		starterDeck.setDeckId(ConnectionUtils.generateUUID().toString());
		starterDeck.setName("Starter deck : " + owner.getUsername());

		// le système attribue des cartes au hasard pour le deck par défaut
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
		if (user == null) {
			throw new BadCredentialsLoginException();
		}
		String hashedSalt = user.getHashedSalt();
		if (ConnectionUtils.hashPassword(password, hashedSalt).equals(user.getPasswordHash())) {
			return user;
		}
		throw new BadCredentialsLoginException();
	}

	public void addUserToConnectedUsers(User user) {
		user = userRepository.findById(user.getId());
		
		if (isThisUserConnected(user)) {
			throw new UserAlreadyConnectedException(user.getUsername());
		} else {
			this.connectedUsers.put(user.getId(), user);
		}
	}

	public boolean isThisUserConnected(User user) {
		return this.connectedUsers.containsKey(user.getId());
	}

	public boolean isThisUserConnected(String userId) {
		return this.connectedUsers.containsKey(userId);
	}

	public void removeUserFromConnectedUsers(String userId) {
		this.connectedUsers.remove(userId);
	}

	public void initDataLists() {
		this.connectedUsers = new HashMap<String, User>();
	}
}
