package com.crave.crave_backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.in.CartInDto;
import com.crave.crave_backend.dto.out.CartItemViewOutDto;
import com.crave.crave_backend.dto.out.CartListViewOutDto;
import com.crave.crave_backend.dto.out.CartViewOutDto;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.entity.Cart;
import com.crave.crave_backend.entity.CartItem;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.exception.CartLimitExceededException;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.repository.CartItemRepository;
import com.crave.crave_backend.repository.CartRepository;
import com.crave.crave_backend.validation.CartValidator;

@Service
public class CartService {

	private final CartRepository cartRepository;
	
	private final CartItemRepository cartItemRepository;
	
	private final CartValidator cartValidator;
		
	private final Logger log = LoggerFactory.getLogger(CartService.class);
	
	@Transactional
	public CartViewOutDto getCart(Long cartId) {
		 Optional<Cart> cartOptional = cartRepository.findByIdForUpdate(cartId);
		 cartValidator.validateCartOptional(cartOptional, cartId);
		 List<CartItemViewOutDto> cartItemsList = cartItemRepository.findCartItemsByCartId(cartId);
		 
		 BigDecimal totalOrderPrice = BigDecimal.ZERO;
		 for (int i = 0; i < cartItemsList.size(); i++) {
			 BigDecimal menuItemTotalPrice = cartItemsList.get(i).getMenuItemTotalPrice();
			 
			 if (menuItemTotalPrice != null) {
				 totalOrderPrice = totalOrderPrice.add(menuItemTotalPrice);
			 }
		 }
		 log.info("event=Successfully fetched cart of user, totalOrderPrice={}", totalOrderPrice);
		 return new CartViewOutDto(cartItemsList, totalOrderPrice);
	}
	
	public List<CartListViewOutDto> getAllCarts() {
		return cartRepository.findCartListByUserId(SecurityUtils.getCurrentUserId());
	}
	
	@Transactional
	public MessageOutDto updateCart(CartInDto cartInDto, MenuItem menuItem, Long restaurantId) {
		Long userId = SecurityUtils.getCurrentUserId();
		Optional<Cart> cartOptional = cartRepository.findByUserIdAndRestaurantIdForUpdate(userId, restaurantId);
		
		if (cartInDto.getQuantity() == 0) {
			if (cartOptional.isEmpty()) {
				log.warn("event=Cart not found and qunatity received=0");
				return new MessageOutDto(SuccessMessageConstants.CART_UPDATED);
			}
			Cart cart = cartOptional.get();
			Long cartId = cart.getId();
			Integer deleteCount = cartItemRepository.deleteByCartIdAndMenuItemId(cartId, menuItem.getId());
			
			if (deleteCount == 0) {
				log.warn("event=Cart item not found and qunatity received=0");
				return new MessageOutDto(SuccessMessageConstants.CART_UPDATED);
			}
			
			if (cart.getItemCount() == 1) {
				cartRepository.deleteById(cart.getId());
			} else {
				cart.setItemCount(cart.getItemCount() - 1);
				cartRepository.save(cart);
			}
			log.info("event=Cart Updated, cartId={}", cart.getId());
		} else {
			if (cartOptional.isEmpty()) {
				List<Cart> cartList = cartRepository.findAllByUserIdForUpdate(userId);
				
				if (cartList.size() >= 2) {
					throw new CartLimitExceededException(ErrorMessageConstants.CART_LIMIT_EXCEEDED);
				}
				
				Cart newCart = new Cart(userId, restaurantId, 1);
				Long newCartId;
				try {
					newCartId = cartRepository.save(newCart).getId();
				} catch (DataIntegrityViolationException dataIntegrityViolationException) {
					String entity = Cart.class.getSimpleName();
					throw new EntityConflictException(
							List.of(String.format(ErrorMessageConstants.SINGLE_ENTITY_CONFLICT, entity)), 
									List.of(entity), LogEventConstants.CART_UPDATE_FAILED
							);
				}
				CartItem cartItem = new CartItem(newCartId,
						menuItem.getId(), menuItem.getName(),
						menuItem.getPrice(), cartInDto.getQuantity());
				
				cartItemRepository.save(cartItem);
				log.info("event=Cart Updated, cartId={}", newCartId);
			} else {
				// Race condition case
				Cart cart = cartOptional.get();
				Optional<CartItem> cartItemOptional = cartItemRepository.findByCartIdAndMenuItemId(cart.getId(), menuItem.getId());
				
				if (cartItemOptional.isEmpty()) {
					cart.setItemCount(cart.getItemCount() + 1);
					cartRepository.save(cart);
					CartItem cartItem = new CartItem(cart.getId(), menuItem.getId(),
							menuItem.getName(), menuItem.getPrice(),
							cartInDto.getQuantity());
					cartItemRepository.save(cartItem);
				} else {
					CartItem cartItem = cartItemOptional.get();
					cartItem.setQuantity(cartInDto.getQuantity());
					cartItemRepository.save(cartItem);
				}
				log.info("event=Cart Updated, cartId={}", cart.getId());
			}
		}
		return new MessageOutDto(SuccessMessageConstants.CART_UPDATED);
	}

	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, CartValidator cartValidator) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.cartValidator = cartValidator;
	}
}
