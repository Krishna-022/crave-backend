package com.crave.crave_backend.mapper;

import java.util.List;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.dto.in.RegisterRestaurantInDto;
import com.crave.crave_backend.entity.Restaurant;

public final class RestaurantMapper {
	
	public static Restaurant toEntity(RegisterRestaurantInDto registerRestaurantInDto, List<byte[]> validatedImages) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(registerRestaurantInDto.getName());
		restaurant.setContactNumber(registerRestaurantInDto.getContactNumber());
		restaurant.setEmail(registerRestaurantInDto.getEmail());
		restaurant.setBuildingNumber(registerRestaurantInDto.getBuildingNumber());
		restaurant.setCityName(registerRestaurantInDto.getCityName());
		restaurant.setPinCode(registerRestaurantInDto.getPinCode());
		restaurant.setStateName(registerRestaurantInDto.getStateName());
		restaurant.setImage(validatedImages.get(0));
		Long userId = SecurityUtils.getCurrentUserId();
		restaurant.setUserId(userId);
		
		return restaurant;
	}
}
