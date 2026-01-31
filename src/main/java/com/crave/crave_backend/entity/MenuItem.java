package com.crave.crave_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;

@Entity
public class MenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long menuCategoryId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Digits(integer = 6, fraction = 2)
	private BigDecimal price;

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

	public Long getMenuCategoryId() {
		return menuCategoryId;
	}

	public void setMenuCategoryId(Long menuCategoryId) {
		this.menuCategoryId = menuCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public MenuItem(Long menuCategoryId, String name, BigDecimal price) {
		super();
		this.menuCategoryId = menuCategoryId;
		this.name = name;
		this.price = price;
	}

	public MenuItem() {
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", menuCategoryId=" + menuCategoryId + ", name=" + name + ", price=" + price
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, id, menuCategoryId, name, price, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(menuCategoryId, other.menuCategoryId) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(updatedAt, other.updatedAt);
	}
}
