package com.crave.crave_backend.configuration.security;

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
