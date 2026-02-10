package com.crave.crave_backend.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.EntityConflictLogConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.dto.out.CursorPage;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.entity.User;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.exception.exceptionTranslator.PersistenceExceptionTranslator;
import com.crave.crave_backend.mapper.MenuItemMapper;
import com.crave.crave_backend.mapper.RestaurantMapper;
import com.crave.crave_backend.repository.MenuCategoryRepository;
import com.crave.crave_backend.repository.MenuItemRepository;
import com.crave.crave_backend.repository.RestaurantRepository;
import com.crave.crave_backend.repository.UserRepository;
import jakarta.validation.Valid;

@Component
public class RestaurantService {

	private final UserRepository userRepository;
	
	private final RestaurantRepository restaurantRepository;
	
	private final MenuCategoryRepository menuCategoryRepository;
	
	private final MenuItemRepository menuItemRepository;
	
	private final Logger log = LoggerFactory.getLogger(RestaurantService.class);
	
	public byte[] getRestaurantImage(Long restaurantId) {
		byte[] image =  restaurantRepository.findRestaurantImageById(restaurantId);
		
		if (image == null ) {
			throw new EntityNotFoundException(LogEventConstants.RESTAURANT_NOT_FOUND, Restaurant.class.getSimpleName(), restaurantId, ErrorMessageConstants.IMAGE_NOT_FOUND);
		}
		return image;
	}
	
	public CursorPage<RestaurantListViewOutDTO> getRestaurants(Long cursor, int limit) {
		if (cursor == null) {
			cursor = 0L;
		}
		
		Pageable pageable = PageRequest.of(0, limit + 1);
		List<RestaurantListViewOutDTO> list = restaurantRepository.getRestaurants(cursor, pageable);
		
		if (list.size() > limit) {
			Long nextCursor = list.get(limit - 1).getId();
			list.remove(limit);
			return new CursorPage<RestaurantListViewOutDTO>(list, true, nextCursor);
		} else {
			return new CursorPage<RestaurantListViewOutDTO>(list, false, null);
		}
	}
	
	@Transactional
	public MessageOutDto registerRestaurant(@Valid RegisterRestaurantInDto registerRestaurantInDto, List<byte[]> validatedImages) {
		
		Long userId = SecurityUtils.getCurrentUserId();
		boolean userExists = userRepository.existsById(userId);
		if (!userExists) {
			throw new EntityNotFoundException(LogEventConstants.REGISTRATION_FAILED, User.class.getSimpleName(), userId, ErrorMessageConstants.USER_NOT_FOUND);
		}

		boolean restaurantExists = restaurantRepository.existsByUserId(userId);
		if (restaurantExists) {
			throw new EntityConflictException(List.of(ErrorMessageConstants.RESTAURANT_LIMIT_EXCEEDED), List.of(EntityConflictLogConstants.USER_ID), LogEventConstants.REGISTRATION_FAILED);
		}
		
		Restaurant restaurant = RestaurantMapper.toEntity(registerRestaurantInDto, validatedImages);
		Long restaurantId;
		try {
			restaurantId = restaurantRepository.save(restaurant).getId();
		} catch (DataIntegrityViolationException ex) {
			throw PersistenceExceptionTranslator.translateRestaurantDataIntegrityViolation(ex, restaurant);
		}
		
		MenuCategory menuCategory = new MenuCategory(restaurantId, registerRestaurantInDto.getMenuCategoryName(), 1);
		Long menuCategoryId =  menuCategoryRepository.save(menuCategory).getId();
		MenuItem menuItem = MenuItemMapper.toEntity(menuCategoryId, registerRestaurantInDto, validatedImages);
		menuItemRepository.save(menuItem);
		
		log.info("event=Restaurant registration successful, restaurantId={}", restaurantId);
		return new MessageOutDto(SuccessMessageConstants.REGISTRATION_SUCCESSFUL);
	}

	public RestaurantService(UserRepository userRepository, RestaurantRepository restaurantRepository,
			MenuCategoryRepository menuCategoryRepository, MenuItemRepository menuItemRepository) {
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
		this.menuCategoryRepository = menuCategoryRepository;
		this.menuItemRepository = menuItemRepository;
	}
}
