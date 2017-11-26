package com.multitiers.test;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.MinionCard;
import com.multitiers.domaine.entity.User;
import com.multitiers.exception.InvalidDeckSizeException;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.DeckEditingService;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@RunWith(MockitoJUnitRunner.class)
public class DeckEditingServiceTest {

	@Mock
	UserRepository userRepositoryMock;

	@InjectMocks
	DeckEditingService deckEditingService;

	
	
	User user;
	Integer deckIndex;
	Deck newDeck;
	Deck newDeckLessThan30Cards;
	Deck newDeckMoreThan30Cards;
	Boolean isNewFavoriteDeck;
	
	@Before
	public void setUp() {
		deckIndex = 0;
		newDeck = new Deck();
		newDeckLessThan30Cards = new Deck();
		newDeckMoreThan30Cards = new Deck();
		
		List<Card> listeCartes = new ArrayList<>();
		for(int i = 0; i < Constantes.DECK_LIST_SIZE; ++i) {
			Card carte = new MinionCard();
			listeCartes.add(carte);
		}
		newDeck.setCardList(listeCartes);
		listeCartes = new ArrayList<>();
		for(int i = 0; i < Constantes.DECK_LIST_SIZE+1; ++i) {
			Card carte = new MinionCard();
			listeCartes.add(carte);
		}
		newDeckMoreThan30Cards.setCardList(listeCartes);
		listeCartes = new ArrayList<>();
		for(int i = 0; i < Constantes.DECK_LIST_SIZE-1; ++i) {
			Card carte = new MinionCard();
			listeCartes.add(carte);
		}
		newDeckLessThan30Cards.setCardList(listeCartes);
		
		isNewFavoriteDeck = true;
		
		String salt = ConnectionUtils.generateSalt();
		user = new User("Username", ConnectionUtils.hashPassword("Password", salt), salt);
		when(userRepositoryMock.findById(user.getId())).thenReturn(user);
		
	}

	@After
	public void tearDown() {
		deckIndex = null;
		newDeck = null;
		isNewFavoriteDeck = null;
		user = null;
	}

	@Test
	public void testSaveDeck() {
		deckEditingService.changeDeck(user, deckIndex, newDeck, isNewFavoriteDeck);
		assertThat(user.getDecks().get(deckIndex)).isEqualTo(newDeck);
	}
	
	@Test(expected=InvalidDeckSizeException.class)
	public void testSaveDeckLessThan30Cards() {
		deckEditingService.changeDeck(user, deckIndex, newDeckLessThan30Cards, isNewFavoriteDeck);
	}
	
	@Test(expected=InvalidDeckSizeException.class)
	public void testSaveDeckMoreThan30Cards() {
		deckEditingService.changeDeck(user, deckIndex, newDeckMoreThan30Cards, isNewFavoriteDeck);
	}

}
