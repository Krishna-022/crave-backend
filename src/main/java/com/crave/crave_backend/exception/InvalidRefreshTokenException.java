package com.crave.crave_backend.exception;

public class InvalidRefreshTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private Long userId;
	
	public InvalidRefreshTokenException(String message, Long userId) {
		this.message = message;
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
