package com.multitiers.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.multitiers.domaine.entity.Card;
import com.multitiers.repository.CardRepository;
import com.multitiers.service.CardCreationService;

public class AuthentificationServiceTest {
	
	CardCreationService cardCreationService = new CardCreationService();
	
	@Autowired
	CardRepository cardRepository;
	
	@Test
	@Ignore
	public void testInsertCustomCardsInDatabase() {
		cardCreationService.initBasicCardSet();
		Card card = cardRepository.findByCardName("Chat noir");
		assertNotNull(card);
	}

}
