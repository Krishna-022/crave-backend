package com.crave.crave_backend.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RefreshToken {
	
	@Id
	@Column(nullable = false, updatable = false)
	private Long userId;
	
	@Column(nullable = false, unique = true)
	private String refreshTokenHash;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public RefreshToken(String refreshTokenHash, Long userId) {
		super();
		this.refreshTokenHash = refreshTokenHash;
		this.userId = userId;
	}

	public String getRefreshTokenHash() {
		return refreshTokenHash;
	}

	public void setRefreshTokenHash(String refreshTokenHash) {
		this.refreshTokenHash = refreshTokenHash;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public RefreshToken() {}
}
