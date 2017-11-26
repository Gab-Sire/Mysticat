package com.multitiers.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.User;
import com.multitiers.exception.InvalidDeckSizeException;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.Constantes;

@Service
public class DeckEditingService {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void changeDeck(User user, Integer deckIndex, Deck newDeck, Boolean isNewFavoriteDeck) {
		if(newDeck.getCardList().size()!=Constantes.CONSTRUCTED_DECK_MAX_SIZE) {
			throw new InvalidDeckSizeException(newDeck.getCardList().size());
		}
		if(newDeck.getName().length()>Constantes.MAX_DECK_NAME_SIZE) {
			throw new RuntimeException("Deck name too long.");
		}
		else if(newDeck.getName().length()<Constantes.MIN_DECK_NAME_SIZE) {
			throw new RuntimeException("Deck name too short.");
		}
		if(deckIndex>=Constantes.MAX_NB_OF_DECKS) {
			throw new RuntimeException("A user can only have up to 3 decks.");
		}
		List<Deck> deckList = user.getDecks();
		if (deckIndex >= user.getDecks().size()) {
			deckIndex = user.getDecks().size();
			deckList.add(newDeck);
		}
		else {
			deckList.set(deckIndex, newDeck);
		}

		if (isNewFavoriteDeck) {
			user.setFavoriteDeck(deckIndex);
		}
		user.setDecks(deckList);
		userRepository.save(user);
	}

}
