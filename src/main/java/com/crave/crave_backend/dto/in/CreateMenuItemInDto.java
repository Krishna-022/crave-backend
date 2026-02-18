package com.crave.crave_backend.dto.in;

import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateMenuItemInDto {

	@NotBlank(message = "Menu item name is required")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]{1,48}[A-Za-z0-9]$", message = "Menu item name must be 3-50 characters, letters and numbers only")
	private String name;

	@NotBlank(message = "Menu item description is required")
	@Size(max = 500, message = "Menu item description cannot exceed 500 letters")
	private String description;

	@NotNull(message = "Price for menu item is required")
	@DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than zero")
	@Digits(integer = 7, fraction = 2, message = "Price can have up to 7 digits and 2 decimal places")
	private BigDecimal price;

	@NotNull(message = "Menu item image is required")
	private MultipartFile menuItemImage;

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getDescription() {
	    return description;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

	public BigDecimal getPrice() {
	    return price;
	}

	public void setPrice(BigDecimal price) {
	    this.price = price;
	}

	public MultipartFile getMenuItemImage() {
		return menuItemImage;
	}

	public void setMenuItemImage(MultipartFile menuItemImage) {
		this.menuItemImage = menuItemImage;
	}
}
