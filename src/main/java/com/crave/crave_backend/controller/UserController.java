package com.crave.crave_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.in.RegisterUserInDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.service.UserService;
import com.crave.crave_backend.validation.UserValidation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.User.BASE)
public class UserController {
	
	private final UserValidation userValidation;

	private final UserService userService;
	
	private Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageOutDto registerUser(@Valid @RequestBody RegisterUserInDto registerUserInDto) {
		log.info("event=User registration request received");
		userValidation.validateRegistrationContactNumberAndEmail(registerUserInDto.getContactNumber(), registerUserInDto.getEmail());
		Long userId = userService.registerUser(registerUserInDto);
		log.info("event=Registration successful, userId={}", userId);
		return new MessageOutDto(SuccessMessageConstants.REGISTRATION_SUCCESSFUL);
	}

	public UserController(UserValidation userValidation, UserService userService) {
		this.userValidation = userValidation;
		this.userService = userService;
	}
}
