package com.crave.crave_backend.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long cartId;

	@Column(nullable = false)
	private Long menuItemId;

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

	public Long getId() {
		return id;
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

	public CartItem(Long cartId, Long menuItemId, Integer quantity) {
		super();
		this.cartId = cartId;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}

	public CartItem() {
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", cartId=" + cartId + ", menuItemId=" + menuItemId + ", quantity=" + quantity
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, createdAt, id, menuItemId, quantity, updatedAt);
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
				&& Objects.equals(id, other.id) && Objects.equals(menuItemId, other.menuItemId)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(updatedAt, other.updatedAt);
	}
}
