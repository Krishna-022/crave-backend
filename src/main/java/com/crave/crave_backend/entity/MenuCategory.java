package com.crave.crave_backend.entity;

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

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = DatabaseConstraintNames.UNIQUE_CATEGORY_NAMES, columnNames = { "restaurant_id", "name" }) })
public class MenuCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long restaurantId;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (menu_item_count > 0)")
	private Integer menuItemCount;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Integer getMenuItemCount() {
		return menuItemCount;
	}

	public void setMenuItemCount(Integer menuItemCount) {
		this.menuItemCount = menuItemCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getId() {
		return id;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuCategory(Long restaurantId, String name, Integer menuItemCount) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.menuItemCount = menuItemCount;
	}

	public MenuCategory() {
	}

	@Override
	public String toString() {
		return "MenuCategory [id=" + id + ", restaurantId=" + restaurantId + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, id, name, restaurantId, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuCategory other = (MenuCategory) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(restaurantId, other.restaurantId)
				&& Objects.equals(updatedAt, other.updatedAt);
	}
}
