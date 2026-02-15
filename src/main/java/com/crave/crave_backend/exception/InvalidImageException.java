package com.crave.crave_backend.exception;

public class InvalidImageException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	private String logEvent;

	public InvalidImageException(String message) {
		this.message = message;
	}

	public String getLogEvent() {
		return logEvent;
	}

	public void setLogEvent(String logEvent) {
		this.logEvent = logEvent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
