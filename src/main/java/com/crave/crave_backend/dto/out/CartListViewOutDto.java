package com.crave.crave_backend.dto.out;

public class CartListViewOutDto {
	
	private Long cartId;
	
    private Integer itemCount;
    
    private String restaurantName;

    public CartListViewOutDto(Long cartId, Integer itemCount, String restaurantName) {
        this.cartId = cartId;
        this.itemCount = itemCount;
        this.restaurantName = restaurantName;
    }

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
}
