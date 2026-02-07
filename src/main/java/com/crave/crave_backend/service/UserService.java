package com.crave.crave_backend.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.crave.crave_backend.dto.in.RegisterUserInDto;
import com.crave.crave_backend.entity.User;
import com.crave.crave_backend.exception.exceptionTranslator.PersistenceExceptionTranslator;
import com.crave.crave_backend.mapper.UserMapper;
import com.crave.crave_backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public Long registerUser(RegisterUserInDto registerUserInDto) {
		User user = UserMapper.toEntity(registerUserInDto);
		user.setPasswordHash(passwordEncoder.encode(registerUserInDto.getPassword()));
		Long userId;
		
		try {
			userId = userRepository.save(user).getId();
		} catch (DataIntegrityViolationException ex) {
			throw PersistenceExceptionTranslator.translateUserDataIntegrityViolation(ex, user);
		}
		return userId;
	}

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
}
