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
public class UserAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long buildingNumber;

	@Column(nullable = false)
	private String cityName;

	@Column(nullable = false, length = 10)
	private String pinCode;

	@Column(nullable = false)
	private String stateName;

	@Column(nullable = false)
	private String countryName;

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

	public Long getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(Long buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public UserAddress(Long userId, Long buildingNumber, String cityName, String pinCode, String stateName,
			String countryName) {
		super();
		this.userId = userId;
		this.buildingNumber = buildingNumber;
		this.cityName = cityName;
		this.pinCode = pinCode;
		this.stateName = stateName;
		this.countryName = countryName;
	}

	public UserAddress() {
	}

	@Override
	public String toString() {
		return "UserAddress [id=" + id + ", userId=" + userId + ", buildingNumber=" + buildingNumber + ", cityName="
				+ cityName + ", pinCode=" + pinCode + ", stateName=" + stateName + ", countryName=" + countryName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(buildingNumber, cityName, countryName, createdAt, id, pinCode, stateName, updatedAt,
				userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAddress other = (UserAddress) obj;
		return Objects.equals(buildingNumber, other.buildingNumber) && Objects.equals(cityName, other.cityName)
				&& Objects.equals(countryName, other.countryName) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && Objects.equals(pinCode, other.pinCode)
				&& Objects.equals(stateName, other.stateName) && Objects.equals(updatedAt, other.updatedAt)
				&& Objects.equals(userId, other.userId);
	}
}
