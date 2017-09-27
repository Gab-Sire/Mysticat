package com.multitiers.exception;

public class UsernameTakenException extends RuntimeException{

	public String username;
	private static final long serialVersionUID = 1L;
	
	public UsernameTakenException(String username) {
		this.username = username;
	}
}
