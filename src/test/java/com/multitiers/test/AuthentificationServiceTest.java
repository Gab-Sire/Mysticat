package com.multitiers.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.multitiers.domaine.entity.Card;
import com.multitiers.repository.CardRepository;
import com.multitiers.service.AuthentificationService;

public class AuthentificationServiceTest {
	
	AuthentificationService authentificationService = new AuthentificationService();
	
	@Autowired
	CardRepository cardRepository;
	
	@Test
	@Ignore
	public void testInsertCustomCardsInDatabase() {
		authentificationService.insertCustomCardsInDatabase();
		Card card = cardRepository.findByCardName("Chat noir");
		assertNotNull(card);
	}

}
