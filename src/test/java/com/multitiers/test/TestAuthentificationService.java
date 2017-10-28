package com.multitiers.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.multitiers.domaine.entity.Card;
import com.multitiers.repository.CardRepository;
import com.multitiers.service.AuthentificationService;

public class TestAuthentificationService {
	
	AuthentificationService authentificationService = new AuthentificationService();
	
	@Autowired
	CardRepository cardRepository;
	
	@Test
	public void testInsertCustomCardsInDatabase() {
		authentificationService.insertCustomCardsInDatabase();
		Card card = cardRepository.findByCardName("Chat noir");
		assertNotNull(card);
	}

}
