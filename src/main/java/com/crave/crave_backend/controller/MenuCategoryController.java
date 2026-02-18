package com.crave.crave_backend.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ApiPathConstants;
import com.crave.crave_backend.dto.in.CreateMenuItemInDto;
import com.crave.crave_backend.dto.out.MenuItemListViewOutDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.mapper.MenuItemMapper;
import com.crave.crave_backend.service.MenuItemService;
import com.crave.crave_backend.validation.MenuItemValidator;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.MenuCategory.BASE)
public class MenuCategoryController {
	
	private final MenuItemService menuItemService;
		
	private final MenuItemValidator menuItemValidator;
	
	private final Logger log = LoggerFactory.getLogger(MenuCategoryController.class);

	@GetMapping(ApiPathConstants.MenuCategory.CATEGORY_ITEMS)
	public List<MenuItemListViewOutDto> getMenuItemsByCategory(@PathVariable Long menuCategoryId) {
	    log.info("event=Request received to fetch menu items, menuCategoryId={}, userId={}", menuCategoryId, SecurityUtils.getCurrentUserId());
	    return menuItemService.getMenuItemsByCategory(menuCategoryId);
	}
	
	@PostMapping(ApiPathConstants.MenuCategory.CATEGORY_ITEMS)
	public MessageOutDto createMenuItem(@PathVariable Long menuCategoryId, @Valid @ModelAttribute CreateMenuItemInDto createMenuItemInDto) {
		log.info("event=request received to create new menu item, UserId={}, menucategoryId={}", SecurityUtils.getCurrentUserId(), menuCategoryId);
		byte[] validatedImage = menuItemValidator.validateCreateMenuItemRequest(menuCategoryId, createMenuItemInDto);		
		return menuItemService.createMenuItem(MenuItemMapper.toEntity(menuCategoryId, createMenuItemInDto, validatedImage));
	}

	public MenuCategoryController(MenuItemService menuItemService, MenuItemValidator menuItemValidator) {
		this.menuItemService = menuItemService;
		this.menuItemValidator = menuItemValidator;
	}
}
