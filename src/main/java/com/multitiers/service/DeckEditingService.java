package com.multitiers.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.User;
import com.multitiers.repository.UserRepository;

@Service
public class DeckEditingService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void changeDeck(User user, Integer deckIndex, Deck newDeck, Boolean isNewFavoriteDeck) {
		List<Deck> deckList = user.getDecks();
		if(deckList.size()<=deckIndex) {
			deckList.add(newDeck);
		}
		else {
			deckList.set(deckIndex, newDeck);	
		}
		if(isNewFavoriteDeck) {
			if(deckIndex>=user.getDecks().size()) {
				deckIndex = user.getDecks().size()-1;
			}
			user.setFavoriteDeck(deckIndex);
		}
		user.setDecks(deckList);
		userRepository.save(user);
	}
	
}
