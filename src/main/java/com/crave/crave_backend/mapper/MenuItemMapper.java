package com.crave.crave_backend.mapper;

import java.util.List;

import com.crave.crave_backend.dto.in.CreateMenuCategoryInDto;
import com.crave.crave_backend.dto.in.CreateMenuItemInDto;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.entity.MenuItem;

public final class MenuItemMapper {
	
	public static MenuItem toEntity(Long categoryId, RegisterRestaurantInDto registerRestaurantInDto, List<byte[]> validatedImages) {
		MenuItem menuItem = new MenuItem();
		menuItem.setMenuCategoryId(categoryId);
		menuItem.setName(registerRestaurantInDto.getMenuItemName());
		menuItem.setPrice(registerRestaurantInDto.getMenuItemPrice());
		menuItem.setImage(validatedImages.get(1));
		menuItem.setDescription(registerRestaurantInDto.getMenuItemDescription());
		
		return menuItem;
	}
	
	public static MenuItem toEntity(Long categoryId, CreateMenuCategoryInDto createMenuCategoryInDto, byte[] validatedImage) {
		MenuItem menuItem = new MenuItem();
		menuItem.setMenuCategoryId(categoryId);
		menuItem.setName(createMenuCategoryInDto.getMenuItemName());
		menuItem.setPrice(createMenuCategoryInDto.getMenuItemPrice());
		menuItem.setImage(validatedImage);
		menuItem.setDescription(createMenuCategoryInDto.getMenuItemDescription());
		
		return menuItem;
	}
	
	public static MenuItem toEntity(Long categoryId, CreateMenuItemInDto createMenuItemInDto, byte[] validatedImage) {
		MenuItem menuItem = new MenuItem();
		menuItem.setMenuCategoryId(categoryId);
		menuItem.setName(createMenuItemInDto.getName());
		menuItem.setPrice(createMenuItemInDto.getPrice());
		menuItem.setImage(validatedImage);
		menuItem.setDescription(createMenuItemInDto.getDescription());
		
		return menuItem;
	}
}
