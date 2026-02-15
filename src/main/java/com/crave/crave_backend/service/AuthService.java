package com.crave.crave_backend.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.crave.crave_backend.config.security.JwtUtils;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.SecurityConstants;
import com.crave.crave_backend.dto.in.LogInInDto;
import com.crave.crave_backend.dto.out.LogInOutDto;
import com.crave.crave_backend.entity.RefreshToken;
import com.crave.crave_backend.entity.User;
import com.crave.crave_backend.exception.ExpiredRefreshJwtException;
import com.crave.crave_backend.exception.InvalidRefreshTokenException;
import com.crave.crave_backend.repository.RefreshTokenRepository;
import com.crave.crave_backend.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;

@Component
public class AuthService {
	
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	private final JwtUtils jwtUtils;
	
	private final Logger log = LoggerFactory.getLogger(AuthService.class);
	
	@Transactional          // Intentionally designed to mitigate timing attacks
	public LogInOutDto authenticateAndLogin(@Valid LogInInDto loginInDto) {
		String givenPassword = loginInDto.getPassword();
		String userPassword = "dummyHash";
		User user = new User();
		Optional<User> userOptional = userRepository.findByContactNumber(loginInDto.getContactNumber());

		if (!userOptional.isEmpty()) {
			user = userOptional.get();
			userPassword = user.getPasswordHash();
		}
		if (!passwordEncoder.matches(givenPassword, userPassword)) {
			throw new BadCredentialsException(ErrorMessageConstants.BAD_CREDENTIALS);
		}
		Long userId = user.getId();
		refreshTokenRepository.deleteByUserId(userId);
		
		String accessToken = jwtUtils.getToken(userId, SecurityConstants.ACCESS_TOKEN_EXPIRATION);
		String refreshToken = jwtUtils.getToken(userId, SecurityConstants.REFRESH_TOKEN_EXPIRATION);
		String refreshTokenHash = jwtUtils.hashRefreshToken(refreshToken);
		
		RefreshToken refreshTokenEntity = new RefreshToken(refreshTokenHash, userId);
		refreshTokenRepository.save(refreshTokenEntity);
		
		log.info("event=User login successful, UserId={}", userId);
		return new LogInOutDto(accessToken, refreshToken);
	}

	public Long verifyRefreshToken(String refreshToken) {
		try {
			return jwtUtils.verifyToken(refreshToken);
		} catch (ExpiredJwtException ex) {
			Long userId = SecurityUtils.getCurrentUserId();
			Integer deletedRowsCount = refreshTokenRepository.deleteByRefreshTokenHash((jwtUtils.hashRefreshToken(refreshToken)));
			
			if (deletedRowsCount > 0) {
				throw new ExpiredRefreshJwtException(ErrorMessageConstants.UNAUTHORIZED, userId);
			}
			else {
				refreshTokenRepository.deleteByUserId(userId);
				throw new InvalidRefreshTokenException(ErrorMessageConstants.UNAUTHORIZED, userId);
			}
		}
	}

	@Transactional(noRollbackFor = InvalidRefreshTokenException.class)
	public LogInOutDto refreshUserSession(String refreshToken, Long userId) {
		String oldRefreshTokenHash = jwtUtils.hashRefreshToken(refreshToken);
		String newRefreshToken = jwtUtils.getToken(userId, SecurityConstants.REFRESH_TOKEN_EXPIRATION);
		String newRefreshTokenHash = jwtUtils.hashRefreshToken(newRefreshToken);
		
		Integer count = refreshTokenRepository.rotateToken(userId, oldRefreshTokenHash, newRefreshTokenHash);
		
		if (count == 0) {
			refreshTokenRepository.deleteByUserId(userId);
			throw new InvalidRefreshTokenException(ErrorMessageConstants.UNAUTHORIZED, userId);
		}
		String accessToken = jwtUtils.getToken(userId, SecurityConstants.ACCESS_TOKEN_EXPIRATION);
		return new LogInOutDto(accessToken, newRefreshToken);
	}
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			RefreshTokenRepository refreshTokenRepository, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtUtils = jwtUtils;
	}
}
