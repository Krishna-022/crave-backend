package com.crave.crave_backend.dto.out;

public class MenuOutDto {
	
    private Long menuCategoryId;
    
    private String menuCategoryName;
    
    private Integer menuItemCount;

	public MenuOutDto(Long menuCategoryId, String menuCategoryName, Integer menuItemCount) {
		this.menuCategoryId = menuCategoryId;
		this.menuCategoryName = menuCategoryName;
		this.menuItemCount = menuItemCount;
	}

	public Long getMenuCategoryId() {
		return menuCategoryId;
	}

	public void setMenuCategoryId(Long menuCategoryId) {
		this.menuCategoryId = menuCategoryId;
	}

	public String getMenuCategoryName() {
		return menuCategoryName;
	}

	public void setMenuCategoryName(String menuCategoryName) {
		this.menuCategoryName = menuCategoryName;
	}

	public Integer getMenuItemCount() {
		return menuItemCount;
	}

	public void setMenuItemCount(Integer menuItemCount) {
		this.menuItemCount = menuItemCount;
	}
}
