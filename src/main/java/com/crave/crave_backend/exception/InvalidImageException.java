package com.crave.crave_backend.exception;

public class InvalidImageException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;

	public InvalidImageException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
