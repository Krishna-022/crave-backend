package com.crave.crave_backend.exception;

public class UnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UnauthorizedException(String message) {
		super(message);
		this.message = message;
	}
}
