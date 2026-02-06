package com.crave.crave_backend.configuration.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.crave.crave_backend.constant.SecurityConstants;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
		
		if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		String accessToken = authHeader.substring(SecurityConstants.BEARER_PREFIX_LENGTH);
		
		try {
			Long userId = jwtUtils.verifyToken(accessToken);
			UserPrincipal userPrincipal = new UserPrincipal(userId);
			
			Authentication authentication = 
					new UsernamePasswordAuthenticationToken(userPrincipal, null, null);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}
		catch (JwtException ex) {
			SecurityContextHolder.clearContext();
			handlerExceptionResolver.resolveException(request, response, null, ex);
		}
	}
}
