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
		if(newDeck.getCardList().size()!=Constantes.DECK_LIST_SIZE) {
			throw new InvalidDeckSizeException(newDeck.getCardList().size());
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
