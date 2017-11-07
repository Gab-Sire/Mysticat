package com.multitiers.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.User;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;

@Service
public class DeckEditingService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private MinionCardRepository minionCardRepository;
	
	@Transactional
	public void editDeck(User user, Integer deckIndex, Deck newDeck) {
		List<Deck> deckList = user.getDecks();
		deckList.set(deckIndex, newDeck);
		user.setDecks(deckList);
	}
	
}
