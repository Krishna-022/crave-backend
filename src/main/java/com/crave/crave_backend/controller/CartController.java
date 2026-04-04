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
import com.crave.crave_backend.dto.out.CartListViewOutDto;
import com.crave.crave_backend.dto.out.CartViewOutDto;
import com.crave.crave_backend.service.CartService;

@RestController
@RequestMapping(ApiPathConstants.Cart.BASE)
public class CartController {
	
	private final CartService cartService;
	
	private final Logger log = LoggerFactory.getLogger(CartController.class);
	
	@GetMapping
	public List<CartListViewOutDto> getAllCarts() {
		log.info("event=Request received to fetch all the carts of user, userId={}", SecurityUtils.getCurrentUserId());
		List<CartListViewOutDto> cartList= cartService.getAllCarts();
		log.info("event={} User carts fetched successfully", cartList.size());
		return cartList;
	}

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping(ApiPathConstants.Cart.BY_ID)
	public CartViewOutDto getCart(@PathVariable Long cartId) {
		log.info("event=Request received to fetch cart, cartId={}, userId={}", cartId, SecurityUtils.getCurrentUserId());
		return cartService.getCart(cartId);
	}
}
