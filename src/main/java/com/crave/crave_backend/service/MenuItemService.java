package com.crave.crave_backend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.dto.out.MenuItemListViewOutDto;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.repository.MenuItemRepository;

@Service
public class MenuItemService {
	
	private final MenuItemRepository menuItemRepository;
	
	public byte[] getMenuItemImage(Long menuItemId) {
		byte[] image = menuItemRepository.findMenuItemImageById(menuItemId);
		
		if (image == null) {
			throw new EntityNotFoundException(String.format(ErrorMessageConstants.IMAGE_NOT_FOUND , EntityAndFieldConstants.MENU_ITEM), EntityAndFieldConstants.MENU_ITEM, menuItemId, ErrorMessageConstants.IMAGE_NOT_FOUND);
		}
		return image;
	}
	
	public MenuItemListViewOutDto getMenuItem(Long menuItemId) {
		Optional<MenuItemListViewOutDto> itemOptional =  menuItemRepository.getItemById(menuItemId);
		
		if (itemOptional.isEmpty()) {
			throw new EntityNotFoundException(LogEventConstants.FAILED_TO_FETCH_MENU_ITEMS, EntityAndFieldConstants.MENU_ITEM, menuItemId, ErrorMessageConstants.ENTITY_NOT_FOUND);
		}
		return itemOptional.get();
	}
	
	public List<MenuItemListViewOutDto> getMenuItemsByCategory(Long menuCategoryId) {
		List<MenuItemListViewOutDto> menuItems = menuItemRepository.findByCategory(menuCategoryId);
		
		if (menuItems.size() == 0) {
			throw new EntityNotFoundException(LogEventConstants.FAILED_TO_FETCH_MENU_ITEMS, EntityAndFieldConstants.MENU_CATEGORY, menuCategoryId, ErrorMessageConstants.ENTITY_NOT_FOUND);
		}
		return menuItems;
	}

	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}
}
