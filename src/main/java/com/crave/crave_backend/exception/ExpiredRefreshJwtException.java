package com.crave.crave_backend.exception;

public class ExpiredRefreshJwtException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private Long userId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ExpiredRefreshJwtException(String message, Long userId) {
		super();
		this.message = message;
		this.userId = userId;
	}
}
