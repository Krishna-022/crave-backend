package com.crave.crave_backend.validation;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.repository.MenuCategoryRepository;

@Component
public class MenuCategoryValidator {

	private final MenuCategoryRepository menuCategoryRepository;
	
	public MenuCategory validateMenuCategoryOptional(Optional<MenuCategory> menuCategoryOptional, MenuItem menuItem) {
		
		if (menuCategoryOptional.isEmpty()) {
			String entity = EntityAndFieldConstants.MENU_CATEGORY;
			throw new EntityNotFoundException(
					LogEventConstants.FAILED_TO_CREATE_MENU_ITEM,
					entity,
					menuItem.getMenuCategoryId(),
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity));
		}
		return menuCategoryOptional.get();
	}

	public byte[] validateCreateMenuCategoryRequest(Long restaurantId, CreateMenuCategoryInDto createMenuCategoryInDto) {
		validateUniqueCategoryNameForRestaurant(restaurantId, createMenuCategoryInDto.getCategoryName());
		return ImageValidator.validateImage(createMenuCategoryInDto.getMenuItemImage());
	}

	private void validateUniqueCategoryNameForRestaurant(Long restaurantId, String categoryName) {
		boolean exists = menuCategoryRepository.existsByRestaurantIdAndName(restaurantId, categoryName);
		if (exists) {
			throw new EntityConflictException(List.of(String.format(ErrorMessageConstants.FIELD_CONFLICT, EntityAndFieldConstants.MENU_CATEGORY_NAME, categoryName)), List.of(EntityAndFieldConstants.MENU_CATEGORY_NAME), LogEventConstants.FAILED_TO_CREATE_MENU_CATEGORY);
		}
	}

	public MenuCategoryValidator(MenuCategoryRepository menuCategoryRepository) {
		this.menuCategoryRepository = menuCategoryRepository;
	}
}
