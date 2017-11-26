package com.multitiers.exception;

public class InvalidDeckSizeException extends RuntimeException{

	private static final long serialVersionUID = -1685549397538973934L;
	
	public InvalidDeckSizeException(int size) {
		super(String.valueOf(size));
	}

}
