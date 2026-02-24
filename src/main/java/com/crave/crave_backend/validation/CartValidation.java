package com.crave.crave_backend.validation;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.repository.MenuCategoryRepository;
import com.crave.crave_backend.repository.MenuItemRepository;

@Component
public class CartValidation {
	
	private final MenuCategoryRepository menuCategoryRepository;
	
	private final MenuItemRepository menuItemRepository;
	
	public MenuItem validateCartUpdateRequest(Long menuItemId, Long restaurantId) {
		Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);

		if (menuItemOptional.isEmpty()) {
			String entity = EntityAndFieldConstants.MENU_ITEM;
			throw new EntityNotFoundException(
					LogEventConstants.CART_UPDATE_FAILED, 
					entity, menuItemId,
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity)
					);
		}
		MenuItem menuItem = menuItemOptional.get();
		Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository.findById(menuItem.getMenuCategoryId());
		
		if (menuCategoryOptional.isEmpty()) {
			String entity = EntityAndFieldConstants.MENU_CATEGORY;
			throw new EntityNotFoundException(
					LogEventConstants.CART_UPDATE_FAILED,
					entity, menuItem.getMenuCategoryId(),
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity)
					);
		}
		
		if (menuCategoryOptional.get().getRestaurantId() != restaurantId) {
			String entity = Restaurant.class.getSimpleName();
			throw new EntityNotFoundException(
					LogEventConstants.CART_UPDATE_FAILED,
					entity, restaurantId,
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity)
					);
		}
		return menuItem;
	}

	public CartValidation(MenuCategoryRepository menuCategoryRepository, MenuItemRepository menuItemRepository) {
		super();
		this.menuCategoryRepository = menuCategoryRepository;
		this.menuItemRepository = menuItemRepository;
	}
}
