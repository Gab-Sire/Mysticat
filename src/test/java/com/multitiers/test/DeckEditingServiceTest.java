package com.multitiers.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.multitiers.repository.UserRepository;
import com.multitiers.service.DeckEditingService;
@RunWith(MockitoJUnitRunner.class)
public class DeckEditingServiceTest {
	
	@Mock
	UserRepository userRepositoryMock;
	
	@InjectMocks
	DeckEditingService deckEditingService;

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
