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

	private static final int OFFSET_MIN_LENGTH = 2;
	private static final int OFFSET_MAX_LENGTH = 4;
	private static final int NB_OF_LETTERS = 26;
	private static final int NB_OF_DIGITS = 10;

	static final char MIN_MAJUSCULE = 'A', MIN_MINUSCULE = 'a', MIN_DIGIT = '0';

	RandomStringGenerator passwordGenerator;
	RandomStringGenerator usernameGenerator;
	Integer passwordLength;
	Integer usernameLength;
	String salt1;
	String salt2;
	String password;
	String hashedPassword;
	String failingPassword;
	String hashedFailingPassword;
	String stringToBeHashed;
	
	Character lowerCaseInUsername;
	Character upperCaseInUsername;
	Character digitInUsername;
	String validUsername;
	String tooShortPassword;
	String tooShortUsername;
	String tooLongUsername;
	String tooLongPassword;

	UUID uuid1;
	UUID uuid2;

	@Before
	public void setUp() throws Exception {
		passwordGenerator = new RandomStringGenerator.Builder()
				.withinRange(ConnectionUtils.MINIMUM_CHAR_PASSWORD, ConnectionUtils.MAXIMUM_CHAR_PASSWORD).build();
		usernameGenerator = new RandomStringGenerator.Builder()
				.withinRange(ConnectionUtils.MINIMUM_CHAR_PASSWORD, ConnectionUtils.MAXIMUM_CHAR_PASSWORD).build();
		stringToBeHashed = usernameGenerator.generate((int)Math.random()*ConnectionUtils.MAX_PASSWORD_LENGTH);
		salt1 = ConnectionUtils.generateSalt();
		salt2 = ConnectionUtils.generateSalt();
		
		
		password = passwordGenerator.generate(randomValidPasswordLength());
		validUsername = usernameGenerator.generate(randomValidUsernameLength());

		validUsername += generateLowerCase();
		validUsername += generateUpperCase();
		validUsername += generateDigit();

		password += generateLowerCase();
		password += generateUpperCase();
		password +=  generateDigit();
		
		hashedPassword = ConnectionUtils.hashPassword(password,salt1);

		uuid1 = ConnectionUtils.generateUUID();
		uuid2 = ConnectionUtils.generateUUID();
		
		tooShortUsername = "";
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
		salt1 = null;
		salt2 = null;
		
		validUsername = null;
		tooShortUsername = null;
		
		uuid1 = null;
		uuid2 = null;
	}

	@Test
	public void hashedPasswordIsDifferentThanPassword() {
		assertFalse(password.equals(hashedPassword));
	}

	@Test
	public void hashesOfTheSameStringsAreTheSame() {
		assertTrue(ConnectionUtils.hashString(stringToBeHashed).equals(ConnectionUtils.hashString(stringToBeHashed)));
	}

	@Test
	public void hashesOfTheSamePasswordAreDifferent() {
		assertFalse(ConnectionUtils.hashPassword(password, salt2).equals(hashedPassword));
	}
	
	@Test
	public void hashesOfTwoDifferentPasswordsAreDifferent() {
		failingPassword = password + "a";
		hashedFailingPassword = ConnectionUtils.hashPassword(failingPassword, salt1);
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
	
	@Test
	public void usernameIsTooShort() {
		// Meet all other conditions, so you know it's length that is the problem.
		tooShortUsername += lowerCaseInUsername;
		tooShortUsername += upperCaseInUsername;
		tooShortUsername += digitInUsername;
		assertFalse(ConnectionUtils.isValidUsername(tooShortUsername));
	}

	@Test
	public void passwordIsTooShort() {
		tooShortPassword = passwordGenerator.generate(ConnectionUtils.MIN_PASSWORD_LENGTH - 1);
		assertFalse(ConnectionUtils.isValidPassword(tooShortPassword));
	}

	@Test
	public void passwordIsTooLong() {
		tooLongPassword = passwordGenerator.generate(ConnectionUtils.MAX_PASSWORD_LENGTH + 1);
		assertFalse(ConnectionUtils.isValidPassword(tooLongPassword));
	}

	@Test
	public void usernameIsTooLong() {
		// Length guaranteed above maximum permitted.
		tooLongUsername = validUsername + usernameGenerator.generate(ConnectionUtils.MAX_USERNAME_LENGTH);
		assertFalse(ConnectionUtils.isValidUsername(tooLongUsername));
	}

	@Test
	public void passwordDoesntContainLowercase() {
		assertFalse(ConnectionUtils.isValidPassword(password.toUpperCase()));
	}
	
	@Test
	public void passwordDoesntContainUppercase() {
		assertFalse(ConnectionUtils.isValidPassword(password.toLowerCase()));
	}
	
	@Test
	public void passwordDoesntContainDigit() {
		assertFalse(ConnectionUtils.isValidPassword(replaceAllDigitsWithLetters(password)));
	}

	private String replaceAllDigitsWithLetters(String str) {
		return str.replaceAll("[0-9]", (Math.random()>0.5) ? ""+generateLowerCase() : ""+generateUpperCase());
	}

	private char generateDigit() {
		return (char) (MIN_DIGIT + Math.random() * NB_OF_DIGITS);
	}

	private char generateUpperCase() {
		return (char) (MIN_MAJUSCULE + Math.random() * NB_OF_LETTERS);
	}

	private char generateLowerCase() {
		return (char) (MIN_MINUSCULE + Math.random() * NB_OF_LETTERS);
	}

	private int randomValidPasswordLength() {
		return (int) (Math.random() * (ConnectionUtils.MAX_PASSWORD_LENGTH - OFFSET_MAX_LENGTH)) + OFFSET_MIN_LENGTH;
	}

	private int randomValidUsernameLength() {
		return (int) (Math.random() * (ConnectionUtils.MAX_USERNAME_LENGTH - OFFSET_MAX_LENGTH)) + OFFSET_MIN_LENGTH;
	}
	
}