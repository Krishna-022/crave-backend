package com.crave.crave_backend.dto.in;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenInDto {
	
	@NotBlank
	private String refreshToken;

	public RefreshTokenInDto() {}

	public RefreshTokenInDto(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
