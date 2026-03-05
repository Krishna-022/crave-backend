package com.crave.crave_backend.constant;

public final class ApiPathConstants {

	private ApiPathConstants() {}
	
	public static final class User {
		
		public static final String BASE = "/user";
	}
	
	public static final class Auth {
		
		public static final String BASE = "/auth";
		
		public static final String LOG_IN = "/login";
		
		public static final String REFRESH = "/refresh";
	}
	
	public static final class Restaurant {
		
		public static final String BASE = "/restaurant";
		
		public static final String BY_ID = "/{restaurantId}";
		
		public static final String MENU = BY_ID + "/menu-category";
		
		public static final String RESTAURANT_IMAGE = BY_ID + "/image";
				
		public static final String UPDATE_CART = BY_ID + "/cart/cart-item/{menuItemId}";
	}
	
	public static final class MenuCategory {
		
		public static final String BASE = "/menu-category";
		
		public static final String BY_ID = "/{menuCategoryId}";
		
		public static final String CATEGORY_ITEMS = BY_ID + "/menu-item";
	}
	
	public static final class MenuItem {
		
		public static final String BASE = "/menu-item";
		
		public static final String BY_ID = "/{menuItemId}";
		
		public static final String MENU_ITEM_IMAGE = BY_ID + "/image";
	}
	
	public static final class Cart {
		
		public static final String BASE = "/cart";
		
		public static final String BY_ID = "/{cartId}";
	}
	
	public static final class PublicApiRoutes {
		
		public static final String REGISTER_USER = User.BASE;
		
		public static final String USER_LOGIN = Auth.BASE + Auth.LOG_IN;
		
		public static final String REFRESH_TOKEN = Auth.BASE + Auth.REFRESH;
	}
}
