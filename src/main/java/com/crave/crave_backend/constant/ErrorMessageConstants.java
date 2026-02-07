package com.crave.crave_backend.constant;

public interface ErrorMessageConstants {
	
	String ENTITY_CONFLICT = "%s with %s '%s' already exists";
	
	String RESTAURANT_LIMIT_EXCEEDED = "Restaurant registration limit exceeded";

	String PERSISTENCE_UNKNOWN_EXCEPTION = "Server busy, please try again";
	
	String DATA_INTEGRITY_VIOLATION = "Please fill all the fields properly";
	
	String BAD_CREDENTIALS = "Invalid credentials";
	
	String RESTAURANT_IMAGE_REQUIRED = "Restaurant image is required";
	
	String INVALID_IMAGE_TYPE = "Invalid image type";
	
	String USAGE_OF_INVALID_REFRESH_TOKEN = "Usage of invalid refresh token";
	
	String LARGE_IMAGE = "Image too large";
	
	String IMAGE_READ_FAILED = "Failed to read image";
	
	String ENTITY_NOT_FOUND = "%S not found";
	
	String UNAUTHORIZED = "Authentication failed";
}
