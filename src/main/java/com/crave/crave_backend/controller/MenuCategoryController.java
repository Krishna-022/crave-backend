package com.crave.crave_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.out.MenuItemListViewOutDto;
import com.crave.crave_backend.service.MenuItemService;

@RestController
@RequestMapping(ApiPathConstants.MenuCategory.BASE)
public class MenuCategoryController {
	
	private final MenuItemService menuItemService;
	
	private final Logger log = LoggerFactory.getLogger(MenuCategoryController.class);

	@GetMapping(ApiPathConstants.MenuCategory.CATEGORY_ITEMS)
	public List<MenuItemListViewOutDto> getMenuItemsByCategory(@PathVariable Long menuCategoryId) {
	    log.info("event=Request received to fetch menu items, menuCategoryId={}, userId={}", menuCategoryId, SecurityUtils.getCurrentUserId());
	    return menuItemService.getMenuItemsByCategory(menuCategoryId);
	}

	public MenuCategoryController(MenuItemService menuItemService) {
		this.menuItemService = menuItemService;
	}
}
