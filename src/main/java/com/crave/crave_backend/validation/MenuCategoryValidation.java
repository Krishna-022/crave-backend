package com.crave.crave_backend.validation;

import java.util.List;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.repository.MenuCategoryRepository;

@Component
public class MenuCategoryValidation {

	private final MenuCategoryRepository menuCategoryRepository;

	public byte[] validateCreateMenuCategoryRequest(Long restaurantId, CreateMenuCategoryInDto createMenuCategoryInDto) {
		validateUniqueCategoryNameForRestaurant(restaurantId, createMenuCategoryInDto.getCategoryName());
		return ImageValidation.validateImage(createMenuCategoryInDto.getMenuItemImage());
	}

	private void validateUniqueCategoryNameForRestaurant(Long restaurantId, String categoryName) {
		boolean exists = menuCategoryRepository.existsByRestaurantIdAndName(restaurantId, categoryName);
		if (exists) {
			throw new EntityConflictException(List.of(String.format(ErrorMessageConstants.FIELD_CONFLICT, EntityAndFieldConstants.MENU_CATEGORY_NAME, categoryName)), List.of(EntityAndFieldConstants.MENU_CATEGORY_NAME), LogEventConstants.FAILED_TO_CREATE_MENU_CATEGORY);
		}
	}

	public MenuCategoryValidation(MenuCategoryRepository menuCategoryRepository) {
		this.menuCategoryRepository = menuCategoryRepository;
	}
}
