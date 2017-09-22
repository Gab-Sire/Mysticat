package com.example.demo;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.multitiers.util.ConnectionUtils;

public class TestConnectionUtils {

	private static final int USERNAME_OFFSET = 4;
	private static final int NB_OF_LETTERS = 26;
	private static final int NB_OF_DIGITS = 10;
	static final char MINIMUM_CHAR_PASSWORD = '!';
	static final char MAXIMUM_CHAR_PASSWORD = '~';

	static final char MIN_MAJUSCULE = 'A',  MIN_MINUSCULE = 'a', MIN_DIGIT = '0';

	RandomStringGenerator passwordGenerator;
	RandomStringGenerator usernameGenerator;
	Integer passwordLength;
	Integer usernameLength;
	String password;
	String hashedPassword;
	String failingPassword;
	String hashedFailingPassword;

	Character lowerCaseInUsername;
	Character upperCaseInUsername;
	Character digitInUsername;
	String validUsername;
	String invalidUsername;
	String tooShortPassword;
	String tooShortUsername;
	String tooLongUsername;
	String tooLongPassword;
	
	String invalidUsernameBecauseLength;

	UUID uuid1;
	UUID uuid2;

	@Before
	public void setUp() throws Exception {
		passwordLength = (int) (Math.random() * ConnectionUtils.MAX_PASSWORD_LENGTH) + 1;
		usernameLength = (int) (Math.random() * (ConnectionUtils.MAX_USERNAME_LENGTH - USERNAME_OFFSET));
		//TODO name constant
		usernameLength+=2;
		passwordGenerator = new RandomStringGenerator.Builder().withinRange(MINIMUM_CHAR_PASSWORD, MAXIMUM_CHAR_PASSWORD)
				.build();
		usernameGenerator = new RandomStringGenerator.Builder().withinRange(MINIMUM_CHAR_PASSWORD, MAXIMUM_CHAR_PASSWORD)
				.build();
		password = passwordGenerator.generate(passwordLength);
		hashedPassword = ConnectionUtils.hashPassword(password);
		
		lowerCaseInUsername = (char) (MIN_MINUSCULE + Math.random() * NB_OF_LETTERS);
		upperCaseInUsername = (char) (MIN_MAJUSCULE + Math.random() * NB_OF_LETTERS);
		digitInUsername = (char) (MIN_DIGIT + Math.random() * NB_OF_DIGITS);
		validUsername = usernameGenerator.generate(usernameLength);
		validUsername+=lowerCaseInUsername;
		validUsername+=upperCaseInUsername;
		validUsername+=digitInUsername;
		
		uuid1 = ConnectionUtils.generateUUID();
		uuid2 = ConnectionUtils.generateUUID();
		
		invalidUsernameBecauseLength = usernameGenerator.generate(ConnectionUtils.MAX_USERNAME_LENGTH+1);
	}

	@After
	public void tearDown() throws Exception {
		passwordLength = null;
		usernameLength = null;
		passwordGenerator = null;
		usernameGenerator = null;
		password = null;
		hashedPassword = null;
		failingPassword = null;

		validUsername = null;
		invalidUsername = null;

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
		failingPassword = password + "a";
		hashedFailingPassword = ConnectionUtils.hashPassword(failingPassword);
		assertFalse(hashedFailingPassword.equals(hashedPassword));
	}

	@Test
	public void twoUUIDsCantBeTheSame() {
		assertNotEquals(uuid1, uuid2);
	}

	@Test
	public void usernameIsValid() {
		assertTrue(ConnectionUtils.isValidUsername(validUsername));
	}
	
	@Test
	public void passwordIsValid() {
		assertTrue(ConnectionUtils.isValidPassword(password));
	}
	
	//TODO Cas inverses (non happy path) pour les 2 derniers tests.
	
	@Test
	public void usernameIsTooShort() {
		//Meet all other conditions, so you know it's length that is the problem.
		tooShortUsername="";
		tooShortUsername+=lowerCaseInUsername;
		tooShortUsername+=upperCaseInUsername;
		tooShortUsername+=digitInUsername;
		assertFalse(ConnectionUtils.isValidUsername(tooShortUsername));
	}
	
	@Test
	public void passwordIsTooShort() {
		tooShortPassword=passwordGenerator.generate(ConnectionUtils.MIN_PASSWORD_LENGTH-1);
		assertFalse(ConnectionUtils.isValidPassword(tooShortPassword));
	}
	
	@Test
	public void passwordIsTooLong() {
		tooLongPassword=passwordGenerator.generate(ConnectionUtils.MAX_PASSWORD_LENGTH+1);
		assertFalse(ConnectionUtils.isValidPassword(tooLongPassword));
	}
	
	@Test
	public void usernameIsTooLong() {
		//Length guaranteed above maximum permitted.
		tooLongUsername = validUsername + usernameGenerator.generate(ConnectionUtils.MAX_USERNAME_LENGTH);
		assertFalse(ConnectionUtils.isValidUsername(tooLongUsername));
	}
}
