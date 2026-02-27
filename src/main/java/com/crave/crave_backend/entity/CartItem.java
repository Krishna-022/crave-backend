package com.crave.crave_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.crave.crave_backend.constant.DatabaseConstraintNames;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Digits;

@Entity
@Table(
	    uniqueConstraints = {@UniqueConstraint(
	            name = DatabaseConstraintNames.UNIQUE_CART_ITEM,
	            columnNames = {"cart_id", "menu_item_id"}
	        )
	    }
	)
public class CartItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
	private Long cartId;

	@Column(nullable = false)
	private Long menuItemId;
	
	@Column(nullable = false)
	private String menuItemName;
	
	@Column(nullable = false)
	@Digits(integer = 6, fraction = 2)
	private BigDecimal unitPrice;
	
	@Column(nullable = false)
	private Integer quantity;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public CartItem(Long cartId, Long menuItemId, String menuItemName, BigDecimal unitPrice, Integer quantity) {
		this.cartId = cartId;
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public CartItem() {
	}

	@Override
	public String toString() {
		return "CartItem [cartId=" + cartId + ", menuItemId=" + menuItemId + ", quantity=" + quantity
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, createdAt, menuItemId, quantity, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(cartId, other.cartId) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(menuItemId, other.menuItemId)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(updatedAt, other.updatedAt);
	}
}
