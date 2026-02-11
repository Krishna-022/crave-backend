package com.crave.crave_backend.dto.in;

import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRestaurantInDto {
	
	@NotBlank(message = "Restaurant name is required")
	@Size(min = 2, max = 100, message = "Restaurant name must be between 2 and 100 characters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Restaurant name must contain only English letters and spaces")
	private String name;

	@NotBlank(message = "Restaurant contact number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Contact number must be exactly 10 digits starting with 6, 7, 8, or 9")
	private String contactNumber;

	@NotBlank(message = "Restaurant email is required")
	@Pattern(regexp = "^[a-z0-9]+(?:\\.[a-z0-9]+)*@gmail\\.com$", message = "Email must be a valid Gmail address (lowercase letters, numbers, periods only; no consecutive/leading/trailing dots)")
	@Size(min = 11, max = 100, message = "Email cannot exceed 100 characters")
	private String email;

	@NotNull(message = "Restaurant building number is required")
	private Long buildingNumber;

	@NotBlank(message = "PINCODE is required")
	@Pattern(regexp = "^\\d{6}$", message = "Pincode must be exactly 6 digits")
	private String pinCode;

	@NotBlank(message = "Restaurant city name is required")
	@Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "City name must contain only English letters and spaces")
	private String cityName;

	@NotBlank(message = "State name is required")
	@Size(min = 2, max = 100, message = "State name must be between 2 and 100 characters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "State name must contain only English letters and spaces")
	private String stateName;

	@NotNull(message = "Restaurant image is required")
	private MultipartFile restaurantImage;

	@NotBlank(message = "Menu category name is required for the menu item")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]{1,48}[A-Za-z0-9]$", message = "Menu category name must be 3–50 characters, letters and numbers only")
	private String menuCategoryName;
	
	@NotBlank(message = "Menu item name is required")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]{1,48}[A-Za-z0-9]$", message = "Menu item name must be 3–50 characters, letters and numbers only")
	private String menuItemName;

	@NotNull(message = "Menu item image is required")
	private MultipartFile menuItemImage;

	@NotBlank(message = "Menu item description is required")
	@Size(max = 500, message = "Menu item description cannot exceed 500 letters")
	private String menuItemDescription;
	
	@NotNull(message = "Price for menu item is required")
	@DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than zero")
	@Digits(integer = 7, fraction = 2, message = "Price can have up to 7 digits and 2 decimal places")
	private BigDecimal menuItemPrice;

	public String getMenuCategoryName() {
		return menuCategoryName;
	}

	public void setMenuCategoryName(String menuCategoryName) {
		this.menuCategoryName = menuCategoryName;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public MultipartFile getMenuItemImage() {
		return menuItemImage;
	}

	public void setMenuItemImage(MultipartFile menuItemImage) {
		this.menuItemImage = menuItemImage;
	}

	public String getMenuItemDescription() {
		return menuItemDescription;
	}

	public void setMenuItemDescription(String menuItemDescription) {
		this.menuItemDescription = menuItemDescription;
	}

	public BigDecimal getMenuItemPrice() {
		return menuItemPrice;
	}

	public void setMenuItemPrice(BigDecimal menuItemPrice) {
		this.menuItemPrice = menuItemPrice;
	}

	public MultipartFile getRestaurantImage() {
		return restaurantImage;
	}

	public void setRestaurantImage(MultipartFile restaurantImage) {
		this.restaurantImage = restaurantImage;
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

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(Long buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
