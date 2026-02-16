package com.crave.crave_backend.exception;

public class UnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private String logEvent;
	
	private String unauthorizedEvent;
	
	public UnauthorizedException(String message, String logEvent, String unauthorizedEvent) {
		super();
		this.message = message;
		this.logEvent = logEvent;
		this.unauthorizedEvent = unauthorizedEvent;
	}
	
	public String getUnauthorizedEvent() {
		return unauthorizedEvent;
	}

	public void setUnauthorizedEvent(String unauthorizedEvent) {
		this.unauthorizedEvent = unauthorizedEvent;
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
