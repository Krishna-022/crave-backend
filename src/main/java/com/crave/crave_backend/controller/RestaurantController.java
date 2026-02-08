package com.crave.crave_backend.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.service.RestaurantService;
import com.crave.crave_backend.validation.RestaurantValidation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.Restaurant.BASE)
public class RestaurantController {
	
	private RestaurantService restaurantService;

	private RestaurantValidation restaurantValidation;

	private Logger log = LoggerFactory.getLogger(RestaurantController.class);

	@PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MessageOutDto registerRestaurant(@Valid @ModelAttribute RegisterRestaurantInDto registerRestaurantInDto) {
		log.info("event=Restaurant registration request received");
		List<byte[]> validatedImages = restaurantValidation.validateRestaurantRegistrationDetails(registerRestaurantInDto);
		return restaurantService.registerRestaurant(registerRestaurantInDto, validatedImages);
	}

	public RestaurantController(RestaurantService restaurantService, RestaurantValidation restaurantValidation) {
		this.restaurantService = restaurantService;
		this.restaurantValidation = restaurantValidation;
	}
}
