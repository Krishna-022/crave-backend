package com.crave.crave_backend.exception.exceptionHandler;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.dto.out.ErrorResponseOutDto;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.exception.ExpiredRefreshJwtException;
import com.crave.crave_backend.exception.InvalidRefreshTokenException;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(EntityConflictException.class)
	public ResponseEntity<ErrorResponseOutDto> handleEntityConflictException(
			EntityConflictException entityConflictException) {
		log.warn("event={} reason=conflicting fields {}", entityConflictException.getLogMessage(),
				entityConflictException.getConflictingFieldsList());
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponseOutDto(entityConflictException.getMessageList()));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponseOutDto> handleBadCredentialsException(
			BadCredentialsException badCredentialsException) {
		log.warn("event=User login failed reason=Bad credentials");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponseOutDto(List.of(badCredentialsException.getMessage())));
	}
	
	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<ErrorResponseOutDto> handleInvalidRefreshTokenException(
			InvalidRefreshTokenException invalidRefreshTokenException) {
		log.warn("event={} user={}", ErrorMessageConstants.USAGE_OF_INVALID_REFRESH_TOKEN , invalidRefreshTokenException.getUserId());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponseOutDto(List.of(invalidRefreshTokenException.getMessage())));
	}
	
	@ExceptionHandler(ExpiredRefreshJwtException.class)
	public ResponseEntity<ErrorResponseOutDto> handleExpiredRefreshJwtException(ExpiredRefreshJwtException expiredRefreshJwtException) {
		log.warn("event=Refresh token expired userId={}", expiredRefreshJwtException.getUserId());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponseOutDto(List.of(ErrorMessageConstants.UNAUTHORIZED)));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseOutDto> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException) {
		List<FieldError> allErrors = methodArgumentNotValidException.getFieldErrors();
		List<String> messageList = new ArrayList<>();

		for (int i = 0; i < allErrors.size(); i++) {
			FieldError err = allErrors.get(i);
			String message = err.getDefaultMessage();
			messageList.add(message);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseOutDto(messageList));
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorResponseOutDto> handleJwtException(JwtException jwtException) {
		log.warn("event=Jwt authentication failed");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponseOutDto(List.of(ErrorMessageConstants.UNAUTHORIZED)));
	}
}
