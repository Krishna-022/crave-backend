package com.crave.crave_backend.dto.out;

import java.math.BigDecimal;
import com.crave.crave_backend.constant.ApiPathConstants;

public class MenuItemListViewOutDto {

	private Long id;
	
	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	private String imageURL;

	public MenuItemListViewOutDto(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = ApiPathConstants.MenuItem.BASE + "/" + id.toString() + "/image";
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageUrl(String imageURL) {
		this.imageURL = imageURL;
	}
}
