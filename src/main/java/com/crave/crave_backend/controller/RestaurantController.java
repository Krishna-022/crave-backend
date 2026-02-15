package com.crave.crave_backend.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.dto.out.CursorPage;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
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
		log.info("event=Restaurant registration request received, userId={}", SecurityUtils.getCurrentUserId());
		List<byte[]> validatedImages = restaurantValidation.validateRestaurantRegistrationDetails(registerRestaurantInDto);
		return restaurantService.registerRestaurant(registerRestaurantInDto, validatedImages);
	}
	
	@GetMapping
	public CursorPage<RestaurantListViewOutDTO> getRestaurants(@RequestParam(required = false) Long cursor, @RequestParam(required = true, defaultValue = "5") int limit) {
		log.info("event=Rquest received to browse restaurants, userId={}, cursor={}, limit={}", SecurityUtils.getCurrentUserId(), cursor, limit);
		CursorPage<RestaurantListViewOutDTO> restaurantListViewCursorPage = restaurantService.getRestaurants(cursor, 5);
		log.info("event=Restaurants browsing request successful, userId={}", SecurityUtils.getCurrentUserId());
		return restaurantListViewCursorPage;
	}
	
	@GetMapping(ApiPathConstants.Restaurant.RESTAURANT_IMAGE)
	public ResponseEntity<byte[]> getRestaurantImage(@PathVariable Long id) {
		log.info("event=Request received to fetch restaurant image, restaurantId={}, userId={}", id, SecurityUtils.getCurrentUserId());
		byte[] image = restaurantService.getRestaurantImage(id);
		log.info("event=Restaurant image fetched successfully");
		return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(image);
	}

	public RestaurantController(RestaurantService restaurantService, RestaurantValidation restaurantValidation) {
		this.restaurantService = restaurantService;
		this.restaurantValidation = restaurantValidation;
	}
}
