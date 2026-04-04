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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.in.CartInDto;
import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.dto.out.CursorPage;
import com.crave.crave_backend.dto.out.MenuOutDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.service.CartService;
import com.crave.crave_backend.service.MenuCategoryService;
import com.crave.crave_backend.service.RestaurantService;
import com.crave.crave_backend.validation.CartValidator;
import com.crave.crave_backend.validation.MenuCategoryValidator;
import com.crave.crave_backend.validation.RestaurantValidator;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.Restaurant.BASE)
public class RestaurantController {

	private final RestaurantService restaurantService;

	private final RestaurantValidator restaurantValidator;

	private final MenuCategoryService menuCategoryService;
		
	private final CartService cartService;
	
	private final CartValidator cartValidator;
	
	private final MenuCategoryValidator menuCategoryValidator;

	private final Logger log = LoggerFactory.getLogger(RestaurantController.class);

	@GetMapping(ApiPathConstants.Restaurant.MENU)
	public List<MenuOutDto> getMenu(@PathVariable Long restaurantId) {
		log.info("event=Request received to fetch menu, restaurantId={}, userId={}", restaurantId, SecurityUtils.getCurrentUserId());
		return menuCategoryService.getMenu(restaurantId);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MessageOutDto registerRestaurant(@Valid @ModelAttribute RegisterRestaurantInDto registerRestaurantInDto) {
		log.info("event=Restaurant registration request received, userId={}", SecurityUtils.getCurrentUserId());
		List<byte[]> validatedImages = restaurantValidator.validateRestaurantRegistrationDetails(registerRestaurantInDto);
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
		byte[] validatedImage = menuCategoryValidator.validateCreateMenuCategoryRequest(restaurantId, createMenuCategoryInDto);
		return menuCategoryService.createMenuCategory(restaurantId, createMenuCategoryInDto, validatedImage);
	}
	
	@PutMapping(ApiPathConstants.Restaurant.UPDATE_CART)
	public MessageOutDto updateCart(@PathVariable Long restaurantId, @PathVariable Long menuItemId, @RequestBody @Valid CartInDto cartInDto) {
		log.info("event=Request received to update the cart, restaurantId={}, menuItemId={}, userId={}", restaurantId, menuItemId, SecurityUtils.getCurrentUserId());
		MenuItem menuItem = cartValidator.validateCartUpdateRequest(menuItemId, restaurantId);
		return cartService.updateCart(cartInDto, menuItem, restaurantId);
	}

	public RestaurantController(RestaurantService restaurantService, RestaurantValidator restaurantValidator, MenuCategoryService menuCategoryService, MenuCategoryValidator menuCategoryValidator, CartValidator cartValidator, CartService cartService) {
		this.restaurantService = restaurantService;
		this.restaurantValidator = restaurantValidator;
		this.menuCategoryService = menuCategoryService;
		this.cartService = cartService;
		this.cartValidator = cartValidator;
		this.menuCategoryValidator = menuCategoryValidator;
	}
}
