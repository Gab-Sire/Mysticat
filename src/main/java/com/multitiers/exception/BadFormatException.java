package com.multitiers.exception;

public class BadFormatException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	String message;
	
	public BadFormatException(String message){
		this.message = message+" est dans un format invalide.";
	}
}
