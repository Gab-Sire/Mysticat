package com.multitiers.exception;

public class BadUsernameFormatException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String username;
	
	public BadUsernameFormatException(String username) {
		this.username = username;
	}
}
