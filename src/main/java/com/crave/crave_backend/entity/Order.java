package com.crave.crave_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.crave.crave_backend.enums.OrderState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;

@Entity
@Table(name = "orders") // because order is reserved keyword in SQL
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long restaurantId;

	@Column(nullable = false)
	private Long userAddressId;

	@Column(nullable = false)
	@Digits(integer = 7, fraction = 2)
	private BigDecimal totalPrice; // Floating point (float/double) introduces rounding errors

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderState orderState;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderState getOrderState() {
		return orderState;
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	public Order(Long userId, Long restaurantId, BigDecimal totalPrice, LocalDate date, OrderState orderState) {
		super();
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.totalPrice = totalPrice;
		this.orderState = orderState;
	}

	public Order() {
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", restaurantId=" + restaurantId + ", totalPrice="
				+ totalPrice + ", orderState=" + orderState + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, id, orderState, restaurantId, totalPrice, updatedAt, userAddressId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& orderState == other.orderState && Objects.equals(restaurantId, other.restaurantId)
				&& Objects.equals(totalPrice, other.totalPrice) && Objects.equals(updatedAt, other.updatedAt)
				&& Objects.equals(userAddressId, other.userAddressId) && Objects.equals(userId, other.userId);
	}
}
