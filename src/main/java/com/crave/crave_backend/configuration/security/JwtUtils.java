package com.crave.crave_backend.configuration.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.constant.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private Logger log = LoggerFactory.getLogger(JwtUtils.class);
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String getToken(Long userId, Long expirationMillis) {
		
		String Token = Jwts.builder()
				.subject(userId.toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expirationMillis))
				.issuer(SecurityConstants.APPLICATION_NAME)
				.signWith(getSecretKey())
				.compact();
		return Token;
	}
	
	public Long verifyToken(String Token) {
		
		String userId = Jwts.parser()
				.requireIssuer(SecurityConstants.APPLICATION_NAME)
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(Token)
				.getPayload()
				.getSubject();
		return Long.parseLong(userId);		
	}
	
	public String hashRefreshToken(String token) {
	    MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(SecurityConstants.SHA_256_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			log.error("event=SHA-256 algorithm unavailable in JVM");
            throw new RuntimeException("Critical startup failure: SHA-256 not supported");
		}
	    byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
	    return Base64.getEncoder().encodeToString(hash);
	}
}
