package com.crave.crave_backend.validation;

import java.util.Optional;
import org.springframework.stereotype.Component;

import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.EntityAndFieldConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.entity.Cart;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.exception.EntityNotFoundException;
import com.crave.crave_backend.exception.UnauthorizedException;
import com.crave.crave_backend.repository.MenuCategoryRepository;
import com.crave.crave_backend.repository.MenuItemRepository;

@Component
public class CartValidator {
	
	private final MenuCategoryRepository menuCategoryRepository;
	
	private final MenuItemRepository menuItemRepository;
	
	public MenuItem validateCartUpdateRequest(Long menuItemId, Long restaurantId) {
		Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);

		if (menuItemOptional.isEmpty()) {
			String entity = EntityAndFieldConstants.MENU_ITEM;
			throw new EntityNotFoundException(
					LogEventConstants.CART_UPDATE_FAILED, 
					entity, menuItemId,
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity)
					);
		}
		MenuItem menuItem = menuItemOptional.get();
		Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository.findById(menuItem.getMenuCategoryId());
		
		if (menuCategoryOptional.isEmpty()) {
			String entity = EntityAndFieldConstants.MENU_CATEGORY;
			throw new EntityNotFoundException(
					LogEventConstants.CART_UPDATE_FAILED,
					entity, menuItem.getMenuCategoryId(),
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity)
					);
		}
		
		if (!menuCategoryOptional.get().getRestaurantId().equals(restaurantId)) {
			String entity = Restaurant.class.getSimpleName();
			throw new EntityNotFoundException(
					LogEventConstants.CART_UPDATE_FAILED,
					entity, restaurantId,
					String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity)
					);
		}
		return menuItem;
	}
	
	public Cart validateCartForOrder(Optional<Cart> cartOptional, Long cartId, Long userId) {
		
		if (cartOptional.isEmpty()) {
			String entity = Cart.class.getSimpleName();
			String message = String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity);
			throw new EntityNotFoundException(message, entity, cartId, message);
		}
		Cart cart = cartOptional.get();
		if (!cart.getUserId().equals(userId)) {
			String entity = Cart.class.getSimpleName();
			throw new UnauthorizedException(ErrorMessageConstants.UNAUTHORIZED,
					ErrorMessageConstants.AUTHORIZATION_FAILED,
					String.format(LogEventConstants.UNAUTHORIZED_ACCESS, userId, entity, cart.getUserId()));
		}
		return cart;
	}
	
	public Cart validateCartOptional(Optional<Cart> cartOptional, Long cartId) {
		if (cartOptional.isEmpty()) {
			 String entity = Cart.class.getSimpleName();
			 String message = String.format(ErrorMessageConstants.ENTITY_NOT_FOUND, entity);
			 throw new EntityNotFoundException(message
					 , entity, cartId, message);
		}
		Cart cart = cartOptional.get();
		Long userId = SecurityUtils.getCurrentUserId();
		if (!cart.getUserId().equals(userId)) {
			String entity = Cart.class.getSimpleName();
			throw new UnauthorizedException(
					 ErrorMessageConstants.UNAUTHORIZED,
					 ErrorMessageConstants.AUTHORIZATION_FAILED,
					 String.format(LogEventConstants.UNAUTHORIZED_ACCESS, userId, entity, cart.getUserId()));
		}
		return cartOptional.get();
	}

	public CartValidator(MenuCategoryRepository menuCategoryRepository, MenuItemRepository menuItemRepository) {
		super();
		this.menuCategoryRepository = menuCategoryRepository;
		this.menuItemRepository = menuItemRepository;
	}
}
