package com.crave.crave_backend.dto.out;

import java.math.BigDecimal;
import java.util.List;

public class CartViewOutDto {

	private List<CartItemViewOutDto> cartItems;
	
	private BigDecimal totalOrderPrice;

	public List<CartItemViewOutDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemViewOutDto> cartItems) {
		this.cartItems = cartItems;
	}

	public BigDecimal getTotalOrderPrice() {
		return totalOrderPrice;
	}

	public void setTotalOrderPrice(BigDecimal totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}

	public CartViewOutDto(List<CartItemViewOutDto> cartItems, BigDecimal totalOrderPrice) {
		this.cartItems = cartItems;
		this.totalOrderPrice = totalOrderPrice;
	}
}
