package com.example.demo;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multitiers.util.ConnectionUtils;

public class TestConnectionUtils {
	static final Integer MAX_PASSWORD_LENGTH = 100;
	static final char MINIMUM_CHAR_PASSWORD = '!';
	static final char MAXIMUM_CHAR_PASSWORD = '~';
	
	RandomStringGenerator passwordGenerator;
	Integer passwordLength;
	String password;
	String hashedPassword;
	String failingPassword;
	String hashedFailingPassword;
	
	UUID uuid1;
	UUID uuid2;
	
	
	@Before
	public void setUp() throws Exception {
		passwordLength = (int)(Math.random()*MAX_PASSWORD_LENGTH);
		passwordGenerator = new RandomStringGenerator.Builder().withinRange(MINIMUM_CHAR_PASSWORD, MAX_PASSWORD_LENGTH).build();
		password = passwordGenerator.generate(passwordLength);
		hashedPassword = ConnectionUtils.hashPassword(password);
		failingPassword = password+"a";
		hashedFailingPassword = ConnectionUtils.hashPassword(failingPassword);
		
		uuid1 = ConnectionUtils.generateUUID();
		uuid2 = ConnectionUtils.generateUUID();
	}

	@After
	public void tearDown() throws Exception {
		passwordLength = null;
		passwordGenerator = null;
		password = null;
		hashedPassword = null;
		failingPassword = null;
		
		uuid1 = null;
		uuid2 = null;
	}
	
	@Test
	public void hashedPasswordIsDifferentThanPassword() {
		assertFalse(password.equals(hashedPassword));
	}
	
	@Test
	public void hashesOfTheSamePasswordAreTheSame() {
		assertTrue(ConnectionUtils.hashPassword(password).equals(hashedPassword));
	}
	
	@Test
	public void hashesOfTwoDifferentPasswordsAreDifferent() {
		assertFalse(hashedFailingPassword.equals(hashedPassword));
	}
	
	@Test public void twoUUIDsCantBeTheSame() {
		assertNotEquals(uuid1, uuid2);
	}
	
	@Test public void usernameIsValid() {
		
	}
	
}
