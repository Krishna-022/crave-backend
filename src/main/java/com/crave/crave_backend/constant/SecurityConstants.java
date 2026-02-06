package com.crave.crave_backend.constant;

import java.util.concurrent.TimeUnit;

public interface SecurityConstants {
	
	String APPLICATION_NAME = "Crave";
	
	String AUTHORIZATION_HEADER = "Authorization";
	
	String BEARER_PREFIX = "Bearer ";
	
	String SHA_256_ALGORITHM = "SHA-256";
	
	Integer BEARER_PREFIX_LENGTH = 7;
	
	Long ACCESS_TOKEN_EXPIRATION = TimeUnit.HOURS.toMillis(1);
	
	Long ref = TimeUnit.MINUTES.toMillis(1);
	
	Long REFRESH_TOKEN_EXPIRATION = TimeUnit.DAYS.toMillis(30);
		
	String REQUEST_ID = "requestId";
}
