package com.crave.crave_backend.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
	
	public static Long getCurrentUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserPrincipal) principal).getUserId();
	}
}
