package com.multitiers.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Player;

public class TestService {
	User user1;
	User user2;
	
	Game game;
	Player player1;
	Player player2;
	
	@Before
	public void setUp() throws Exception {
		user1 = new User();
		user2 = new User();
		
		player1 = new Player(user1);
		player2 = new Player(user2);
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Test
	public void validCredentialsSignUp() {
		fail();
	}

}
