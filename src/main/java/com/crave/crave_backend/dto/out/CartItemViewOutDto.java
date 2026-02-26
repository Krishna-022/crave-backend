package com.crave.crave_backend.dto.out;

import java.math.BigDecimal;

public class CartItemViewOutDto {
	
	String menuItemName;
	
	Integer quantity;
	
	BigDecimal unitPrice;
	
	BigDecimal menuItemTotalPrice;

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getMenuItemTotalPrice() {
		return menuItemTotalPrice;
	}

	public void setMenuItemTotalPrice(BigDecimal menuItemTotalPrice) {
		this.menuItemTotalPrice = menuItemTotalPrice;
	}

	public CartItemViewOutDto(String menuItemName, Integer quantity, BigDecimal unitPrice) {
		this.menuItemName = menuItemName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.menuItemTotalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
	}
}
