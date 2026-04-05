package com.crave.crave_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.in.RegisterUserInDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
import com.crave.crave_backend.service.RestaurantService;
import com.crave.crave_backend.service.UserService;
import com.crave.crave_backend.validation.UserValidator;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.User.BASE)
public class UserController {
	
	private final UserValidator userValidator;

	private final UserService userService;
	
	private final RestaurantService restaurantService;
	
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageOutDto registerUser(@Valid @RequestBody RegisterUserInDto registerUserInDto) {
		log.info("event=User registration request received");
		userValidator.validateRegistrationContactNumberAndEmail(registerUserInDto.getContactNumber(), registerUserInDto.getEmail());
		Long userId = userService.registerUser(registerUserInDto);
		log.info("event=Registration successful, userId={}", userId);
		return new MessageOutDto(SuccessMessageConstants.REGISTRATION_SUCCESSFUL);
	}
	
	@GetMapping(ApiPathConstants.Restaurant.BASE)
	public List<RestaurantListViewOutDTO> getMyRestaurants() {
		Long userId = SecurityUtils.getCurrentUserId();
		log.info("event=Request received to fetch current user's restaurants, userId={}", userId);
		List<RestaurantListViewOutDTO> restaurantList = restaurantService.getMyRestaurants(userId);
		log.info("event=Current user's restaurants fetched successfully, userId={}, count={}", userId, restaurantList.size());
		return restaurantList;
	}

	public UserController(UserValidator userValidator, UserService userService, RestaurantService restaurantService) {
		this.userValidator = userValidator;
		this.userService = userService;
		this.restaurantService = restaurantService;
	}
}
