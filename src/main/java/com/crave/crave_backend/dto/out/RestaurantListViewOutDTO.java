package com.crave.crave_backend.dto.out;

import com.crave.crave_backend.constant.ApiPathConstants;

public class RestaurantListViewOutDTO {
	
	private Long id;
	
	private String name;
	
	private String cityName;
	
	private String imageURL;

	public RestaurantListViewOutDTO(Long id, String name, String cityName) {
		this.id = id;
		this.name = name;
		this.cityName = cityName;
		this.imageURL = ApiPathConstants.Restaurant.BASE + "/" + id.toString() + "/image";
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
