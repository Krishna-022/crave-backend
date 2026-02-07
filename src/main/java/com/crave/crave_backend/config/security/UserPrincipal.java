package com.crave.crave_backend.config.security;

public class UserPrincipal {
	
	private final Long userId;

	public Long getUserId() {
		return userId;
	}

	public UserPrincipal(Long userId) {
		super();
		this.userId = userId;
	}
}
