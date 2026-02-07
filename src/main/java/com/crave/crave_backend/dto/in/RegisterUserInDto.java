package com.crave.crave_backend.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserInDto {
	
	@NotBlank(message = "First name is required")
	@Pattern(
			regexp = "^[A-Za-z]{2,50}$",
			message = "First name must be 2 to 50 English alphabets only"
			)
	private String firstName;
	
	@Pattern(
		    regexp = "^[A-Za-z]{2,50}$",
		    message = "Middle name must be 2 to 50 English letters only if provided"
		    )
	private String middleName;
	
	@NotBlank(message = "Last name is required")
	@Pattern(
		    regexp = "^[A-Za-z]{2,50}$",
		    message = "Last name must be 2 to 50 English letters only, with no spaces"
		    )
	private String lastName;
	
	@NotBlank(message = "Contact number is required")
	@Pattern(
			regexp = "^[6-9]\\d{9}$",
			message = "Contact number must be exactly 10 digits starting with 6, 7, 8, or 9"
			)
	private String contactNumber;
	
	@NotBlank(message = "Email is required")
	@Pattern(
		    regexp = "^[a-z0-9]+(?:\\.[a-z0-9]+)*@gmail\\.com$",
		    message = "Email must be a valid Gmail address (lower case letters, numbers, periods only; no consecutive/leading/trailing dots)"
		    )
	@Size(min = 11, max = 100, message = "Email cannot exceed 100 characters")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
	@Pattern(
		    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
		    message = "Password must contain: uppercase, lowercase, number, and one of @$!%*?&"
		    )
	private String password;

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "RegisterUserInDto [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", contactNumber=" + contactNumber + ", email=" + email + ", password=" + password + "]";
	}
}
