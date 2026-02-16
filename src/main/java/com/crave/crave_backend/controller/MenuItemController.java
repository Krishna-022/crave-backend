package com.crave.crave_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.out.MenuItemListViewOutDto;
import com.crave.crave_backend.service.MenuItemService;

@RestController
@RequestMapping(ApiPathConstants.MenuItem.BASE)
public class MenuItemController {
	
	private final MenuItemService menuItemService;
	
	private final Logger log = LoggerFactory.getLogger(MenuItemController.class);
	
	@GetMapping(ApiPathConstants.MenuItem.MENU_ITEM_IMAGE)
	public ResponseEntity<byte[]> getMenuItemImage(@PathVariable Long menuItemId) {
		log.info("event=Request received to fetch menu item image, menuItemId={}, userId={}", menuItemId, SecurityUtils.getCurrentUserId());
		byte[] image = menuItemService.getMenuItemImage(menuItemId);
		log.info("event=Menu item image fetched successfully");
		return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(image);
	}
	
	@GetMapping(ApiPathConstants.MenuItem.BY_ID)
	public MenuItemListViewOutDto getMenuItem(@PathVariable Long menuItemId) {
		log.info("event=Request received to fetch menu item, menuItemId={}, userId={}", menuItemId, SecurityUtils.getCurrentUserId());
		MenuItemListViewOutDto menuItemListViewOutDto = menuItemService.getMenuItem(menuItemId);
		log.info("event=Menu item fetched successfully, menuItemId={}", menuItemListViewOutDto.getId());
		return menuItemListViewOutDto;
	}

	public MenuItemController(MenuItemService menuItemService) {
		this.menuItemService = menuItemService;
	}
 }
