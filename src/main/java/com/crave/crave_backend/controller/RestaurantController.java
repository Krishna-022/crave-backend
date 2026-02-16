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
import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.dto.out.CursorPage;
import com.crave.crave_backend.dto.out.MenuOutDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
import com.crave.crave_backend.service.MenuCategoryService;
import com.crave.crave_backend.service.RestaurantService;
import com.crave.crave_backend.validation.MenuCategoryValidation;
import com.crave.crave_backend.validation.RestaurantValidation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.Restaurant.BASE)
public class RestaurantController {

	private final RestaurantService restaurantService;

	private final RestaurantValidation restaurantValidation;

	private final MenuCategoryService menuCategoryService;

	private final MenuCategoryValidation menuCategoryValidation;

	private final Logger log = LoggerFactory.getLogger(RestaurantController.class);

	@GetMapping(ApiPathConstants.Restaurant.MY)
	public List<RestaurantListViewOutDTO> getMyRestaurants() {
		Long userId = SecurityUtils.getCurrentUserId();
		log.info("event=Request received to fetch current user's restaurants, userId={}", userId);
		List<RestaurantListViewOutDTO> restaurantList = restaurantService.getMyRestaurants(userId);
		log.info("event=Current user's restaurants fetched successfully, userId={}, count={}", userId, restaurantList.size());
		return restaurantList;
	}

	@GetMapping(ApiPathConstants.Restaurant.MENU)
	public List<MenuOutDto> getMenu(@PathVariable Long restaurantId) {
		log.info("event=Request received to fetch menu, restaurantId={}, userId={}", restaurantId, SecurityUtils.getCurrentUserId());
		return menuCategoryService.getMenu(restaurantId);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
	public ResponseEntity<byte[]> getRestaurantImage(@PathVariable Long restaurantId) {
		log.info("event=Request received to fetch restaurant image, restaurantId={}, userId={}", restaurantId, SecurityUtils.getCurrentUserId());
		byte[] image = restaurantService.getRestaurantImage(restaurantId);
		log.info("event=Restaurant image fetched successfully");
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

	@PostMapping(path = ApiPathConstants.Restaurant.MENU, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MessageOutDto createMenuCategory(@PathVariable Long restaurantId, @Valid @ModelAttribute CreateMenuCategoryInDto createMenuCategoryInDto) {
		log.info("event=Request received to create menu category, restaurantId={}, userId={}", restaurantId, SecurityUtils.getCurrentUserId());
		byte[] validatedImage = menuCategoryValidation.validateCreateMenuCategoryRequest(restaurantId, createMenuCategoryInDto);
		return menuCategoryService.createMenuCategory(restaurantId, createMenuCategoryInDto, validatedImage);
	}

	public RestaurantController(RestaurantService restaurantService, RestaurantValidation restaurantValidation, MenuCategoryService menuCategoryService, MenuCategoryValidation menuCategoryValidation) {
		this.restaurantService = restaurantService;
		this.restaurantValidation = restaurantValidation;
		this.menuCategoryService = menuCategoryService;
		this.menuCategoryValidation = menuCategoryValidation;
	}
}
