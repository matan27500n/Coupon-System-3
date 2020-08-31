package com.johnbryce.app.exceptions;

public class AlreadyExitsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExitsException() {
		super("The company is already exists in the system");
	}

	public AlreadyExitsException(String message) {
		super(message);
	}

}
