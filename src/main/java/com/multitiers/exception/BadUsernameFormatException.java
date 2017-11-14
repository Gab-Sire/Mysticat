package com.multitiers.exception;

public class BadUsernameFormatException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	String username;
	
	public BadUsernameFormatException(String username) {
		super(username);
		this.username = username;
	}
}
