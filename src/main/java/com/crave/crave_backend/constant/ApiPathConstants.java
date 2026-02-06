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
		
		public static final String LIST = "/list";
		
	}
	
	public static final class PublicApiRoutes {
		
		public static final String REGISTER_USER = User.BASE;
		
		public static final String USER_LOGIN = Auth.BASE + Auth.LOG_IN;
		
		public static final String REFRESH_TOKEN = Auth.BASE + Auth.REFRESH;
	}
}
