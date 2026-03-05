package com.crave.crave_backend.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.dto.out.MenuOutDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.exception.UnauthorizedException;
import com.crave.crave_backend.exception.exceptionTranslator.PersistenceExceptionTranslator;
import com.crave.crave_backend.mapper.MenuItemMapper;
import com.crave.crave_backend.repository.MenuCategoryRepository;
import com.crave.crave_backend.repository.MenuItemRepository;
import com.crave.crave_backend.repository.RestaurantRepository;

@Service
public class MenuCategoryService {

	private final MenuCategoryRepository menuCategoryRepository;

	private final RestaurantRepository restaurantRepository;

	private final MenuItemRepository menuItemRepository;

	private final Logger log = LoggerFactory.getLogger(MenuCategoryService.class);

	public List<MenuOutDto> getMenu(Long restaurantId) {
		List<MenuOutDto> menu = menuCategoryRepository.getMenu(restaurantId);

		if (menu.size() == 0) {
			throw new EntityNotFoundException(LogEventConstants.FAILED_TO_FETCH_MENU, Restaurant.class.getSimpleName(),
					restaurantId, ErrorMessageConstants.ENTITY_NOT_FOUND);
		}
		log.info("event=Menu fetched successfully");
		return menu;
	}

	@Transactional
	public MessageOutDto createMenuCategory(Long restaurantId, CreateMenuCategoryInDto createMenuCategoryInDto, byte[] validatedMenuItemImage) {
		Optional<Restaurant> restaurantOptional = restaurantRepository.findByIdForUpdate(restaurantId);
		
		if (restaurantOptional.isEmpty()) {
			String entity = Restaurant.class.getSimpleName();
			throw new EntityNotFoundException(LogEventConstants.FAILED_TO_CREATE_MENU_CATEGORY, entity, restaurantId, String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity));
		}
		
		if (restaurantOptional.get().getUserId() != SecurityUtils.getCurrentUserId()) {
			throw new UnauthorizedException(ErrorMessageConstants.UNAUTHORIZED, LogEventConstants.FAILED_TO_CREATE_MENU_CATEGORY, String.format(LogEventConstants.UNAUTHORIZED_ACCESS, SecurityUtils.getCurrentUserId(), Restaurant.class.getSimpleName(), restaurantOptional.get().getUserId()));
		}

		MenuCategory menuCategory = new MenuCategory(restaurantId, createMenuCategoryInDto.getCategoryName(), 1);
		
		Long menuCategoryId;
		try {
			menuCategoryId = menuCategoryRepository.save(menuCategory).getId();
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			throw PersistenceExceptionTranslator.translateMenuCategoryDataIntegrityViolation(dataIntegrityViolationException, createMenuCategoryInDto.getCategoryName());
		}

		MenuItem menuItem = MenuItemMapper.toEntity(menuCategoryId, createMenuCategoryInDto, validatedMenuItemImage);
		menuItemRepository.save(menuItem);

		log.info("event=Menu category created successfully, restaurantId={}, menuCategoryId={}", restaurantId, menuCategoryId);
		return new MessageOutDto(SuccessMessageConstants.MENU_CATEGORY_CREATED);
	}

	public MenuCategoryService(MenuCategoryRepository menuCategoryRepository, RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
		this.menuCategoryRepository = menuCategoryRepository;
		this.restaurantRepository = restaurantRepository;
		this.menuItemRepository = menuItemRepository;
	}
}
