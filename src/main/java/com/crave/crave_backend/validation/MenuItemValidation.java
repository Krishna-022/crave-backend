package com.crave.crave_backend.validation;

import java.util.List;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.dto.in.CreateMenuItemInDto;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.repository.MenuItemRepository;

@Component
public class MenuItemValidation {
	
	private final MenuItemRepository menuItemRepository;
	
	public byte[] validateCreateMenuItemRequest(Long menuCategoryId, CreateMenuItemInDto createMenuItemInDto) {
		validateUniqueMenuItemNameForCategory(menuCategoryId, createMenuItemInDto.getName());
		return ImageValidation.validateImage(createMenuItemInDto.getMenuItemImage());
	}
	
	public void validateUniqueMenuItemNameForCategory(Long menuCategoryId, String menuItemName) {
		boolean exists = menuItemRepository.existsByMenuCategoryIdAndNameIgnoreCase(menuCategoryId, menuItemName);
		if (exists) {
			throw new EntityConflictException(
					List.of(String.format(ErrorMessageConstants.ENTITY_CONFLICT, EntityAndFieldConstants.MENU_ITEM, EntityAndFieldConstants.NAME, menuItemName)), 
					List.of(EntityAndFieldConstants.NAME),
					LogEventConstants.FAILED_TO_CREATE_MENU_ITEM
					);
		}
	}

	public MenuItemValidation(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}
}
