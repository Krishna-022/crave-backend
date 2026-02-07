package com.crave.crave_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.in.LogInInDto;
import com.crave.crave_backend.dto.out.LogInOutDto;
import com.crave.crave_backend.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.Auth.BASE)
public class AuthController {
	
	private AuthService authService;

	private Logger log = LoggerFactory.getLogger(AuthController.class);

	@PostMapping(ApiPathConstants.Auth.LOG_IN)
	public LogInOutDto loginUser(@Valid @RequestBody LogInInDto loginInDto) {
		log.info("event=Received user login request");
		return authService.authenticateAndLogin(loginInDto);
	}
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
}
