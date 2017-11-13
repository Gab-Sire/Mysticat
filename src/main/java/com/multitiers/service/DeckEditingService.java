package com.multitiers.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.User;

@Service
public class DeckEditingService {
	
	@Transactional
	public void changeDeck(User user, Integer deckIndex, Deck newDeck) {
		List<Deck> deckList = user.getDecks();
		if(deckList.size()<=deckIndex) {
			deckList.add(newDeck);
		}
		else {
			deckList.set(deckIndex, newDeck);	
		}
		user.setDecks(deckList);
	}
	
}
