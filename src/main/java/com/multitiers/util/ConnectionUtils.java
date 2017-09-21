package com.multitiers.util;

import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import com.multitiers.domaine.User;

public class ConnectionUtils {
	public static final String SALT = "sha1myboy";
	
	//Une lettre majuscule, une lettre minuscule, un chiffre, longueur minimale 6 caracteres.
	public final static String USERNAME_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
	public final static String PASSWORD_REGEX = "";
	
	//Yeah, I wrote that. (not)
	public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
			+ "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0"
			+ "c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\"
			+ "x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
			+ "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]"
			+ "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|"
			+ "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x"
			+ "5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	public static String hashPassword(String password){
		return DigestUtils.sha1Hex(password+SALT);
	}
	
	public static UUID generateUUID() {
		return UUID.randomUUID();
	}
	
	public static Boolean isCorrectPasswords(String password, String hashedPassword) {
		return hashPassword(password).equals(hashedPassword);
	}
	
	
	public static Boolean isValidEmailFormat(String email) {
		return Pattern.matches(EMAIL_REGEX, email);
	}
	
	public static Boolean isValidUsername(String username) {
		return Pattern.matches(USERNAME_REGEX, username);
	}
}
