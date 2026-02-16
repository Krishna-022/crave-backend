package com.crave.crave_backend.validation;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.exception.InvalidImageException;
import com.crave.crave_backend.repository.MenuCategoryRepository;

@Component
public class MenuCategoryValidation {

	private final MenuCategoryRepository menuCategoryRepository;

	public byte[] validateCreateMenuCategoryRequest(Long restaurantId, CreateMenuCategoryInDto createMenuCategoryInDto) {
		validateUniqueCategoryNameForRestaurant(restaurantId, createMenuCategoryInDto.getCategoryName());
		return validateImage(createMenuCategoryInDto.getMenuItemImage());
	}

	private void validateUniqueCategoryNameForRestaurant(Long restaurantId, String categoryName) {
		boolean exists = menuCategoryRepository.existsByRestaurantIdAndName(restaurantId, categoryName);
		if (exists) {
			throw new EntityConflictException(List.of(String.format(ErrorMessageConstants.FIELD_CONFLICT, EntityAndFieldConstants.MENU_CATEGORY_NAME, categoryName)), List.of(EntityAndFieldConstants.MENU_CATEGORY_NAME), LogEventConstants.FAILED_TO_CREATE_MENU_CATEGORY);
		}
	}

	private byte[] validateImage(MultipartFile image) {
		if (image == null || image.isEmpty()) {
			throw new InvalidImageException(ErrorMessageConstants.IMAGE_REQUIRED);
		}
		String contentType = image.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new InvalidImageException(ErrorMessageConstants.INVALID_IMAGE_TYPE);
		}
		if (image.getSize() > 2 * 1024 * 1024) {
			throw new InvalidImageException(ErrorMessageConstants.LARGE_IMAGE);
		}
		try {
			return image.getBytes();
		} catch (IOException ex) {
			throw new InvalidImageException(ErrorMessageConstants.IMAGE_READ_FAILED);
		}
	}

	public MenuCategoryValidation(MenuCategoryRepository menuCategoryRepository) {
		this.menuCategoryRepository = menuCategoryRepository;
	}
}
