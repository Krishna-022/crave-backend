package com.crave.crave_backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crave.crave_backend.constant.DatabaseConstraintNames;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.out.MenuItemListViewOutDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.repository.MenuCategoryRepository;
import com.crave.crave_backend.repository.MenuItemRepository;
import com.crave.crave_backend.validation.MenuCategoryValidator;

@Service
public class MenuItemService {
	
	private final MenuItemRepository menuItemRepository;
	
	private final MenuCategoryRepository menuCategoryRepository;
	
	private final MenuCategoryValidator menuCategoryValidator;
	
	private final Logger log = LoggerFactory.getLogger(MenuItemService.class);
	
	@Transactional
	public MessageOutDto createMenuItem(MenuItem menuItem) {
		Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository.findByIdForUpdate(menuItem.getMenuCategoryId());
		MenuCategory menuCategory = menuCategoryValidator.validateMenuCategoryOptional(menuCategoryOptional, menuItem);
		
		Long menuItemId = 0L;
		try {
			menuItemId = menuItemRepository.save(menuItem).getId();
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			String info = dataIntegrityViolationException.getMostSpecificCause().toString().toLowerCase();
			
			if (info.contains(DatabaseConstraintNames.UNIQUE_MENU_ITEM_NAME)) {
				throw new EntityConflictException(
						List.of(String.format(ErrorMessageConstants.ENTITY_CONFLICT, EntityAndFieldConstants.MENU_ITEM, EntityAndFieldConstants.NAME, menuItem.getName())), 
						List.of(EntityAndFieldConstants.NAME),
						LogEventConstants.FAILED_TO_CREATE_MENU_ITEM
						);
			}
		} 
		menuCategory.setMenuItemCount(menuCategory.getMenuItemCount() + 1);
		menuCategoryRepository.save(menuCategory);
		
		log.info("event=Menu item created, menuItemId={}", menuItemId);
		return new MessageOutDto(SuccessMessageConstants.MENU_ITEM_CREATED);
	}
	
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

	public MenuItemService(MenuItemRepository menuItemRepository, MenuCategoryRepository menuCategoryRepository, MenuCategoryValidator menuCategoryValidator) {
		this.menuItemRepository = menuItemRepository;
		this.menuCategoryRepository = menuCategoryRepository;
		this.menuCategoryValidator = menuCategoryValidator;
	}
}
