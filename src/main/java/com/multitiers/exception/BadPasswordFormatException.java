package com.multitiers.exception;

public class BadPasswordFormatException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	String password;
	
	public BadPasswordFormatException(String password){
		this.password = password;
	}
}
