package com.crave.crave_backend.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.dto.out.MenuOutDto;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.repository.MenuCategoryRepository;

@Service
public class MenuCategoryService {
	
	private final MenuCategoryRepository menuCategoryRepository;
	
	private final Logger log = LoggerFactory.getLogger(MenuCategoryService.class);
	
	public List<MenuOutDto> getMenu(Long restaurantId) {
		List<MenuOutDto> menu = menuCategoryRepository.getMenu(restaurantId);
		
		if (menu.size() == 0) {
			throw new EntityNotFoundException(LogEventConstants.FAILED_TO_FETCH_MENU, Restaurant.class.getSimpleName(), restaurantId, ErrorMessageConstants.ENTITY_NOT_FOUND);
		}
		log.info("event=Menu fetched successfully");
		return menu;
	}

	public MenuCategoryService(MenuCategoryRepository menuCategoryRepository) {
		this.menuCategoryRepository = menuCategoryRepository;
	}
}
