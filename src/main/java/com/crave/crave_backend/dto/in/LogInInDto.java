package com.crave.crave_backend.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LogInInDto {
	
	@NotBlank(message = "Contact number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Contact number must be exactly 10 digits starting with 6, 7, 8, or 9")
	private String contactNumber;

	@NotBlank(message = "Password is required")
	private String password;

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
