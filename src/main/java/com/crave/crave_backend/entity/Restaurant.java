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
		@UniqueConstraint(name = DatabaseConstraintNames.UNIQUE_CONTACT_NUMBER, columnNames = "contact_number"),
		@UniqueConstraint(name = DatabaseConstraintNames.UNIQUE_EMAIL, columnNames = "email"),
		@UniqueConstraint(name = DatabaseConstraintNames.UNIQUE_NAME, columnNames = "name") })
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, unique = true, length = 10)
	private String contactNumber;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private Long buildingNumber;

	@Column(nullable = false)
	private String cityName;

	@Column(nullable = false, length = 10)
	private String pinCode;

	@Column(nullable = false)
	private String stateName;

	@Column(columnDefinition = "BYTEA")
	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Restaurant() {
	}

	public Restaurant(Long userId, String name, String contactNumber, String email, Long buildingNumber,
			String cityName, String pinCode, String stateName) {
		this.userId = userId;
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.buildingNumber = buildingNumber;
		this.cityName = cityName;
		this.pinCode = pinCode;
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return "Restaurant{" + "id=" + id + ", userId=" + userId + ", name='" + name + '\'' + ", contactNumber='"
				+ contactNumber + '\'' + ", email='" + email + '\'' + ", buildingNumber=" + buildingNumber
				+ ", cityName='" + cityName + '\'' + ", pinCode='" + pinCode + '\'' + ", stateName='" + stateName + '\''
				+ '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(buildingNumber, cityName, contactNumber, createdAt, email, id, name, pinCode, stateName,
				updatedAt, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		return Objects.equals(buildingNumber, other.buildingNumber) && Objects.equals(cityName, other.cityName)
				&& Objects.equals(contactNumber, other.contactNumber) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(pinCode, other.pinCode)
				&& Objects.equals(stateName, other.stateName) && Objects.equals(updatedAt, other.updatedAt)
				&& Objects.equals(userId, other.userId);
	}
}
