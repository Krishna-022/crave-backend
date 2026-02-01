package com.crave.crave_backend.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.crave.crave_backend.constant.ApiPathConstants;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain customSecurityFilterChain(HttpSecurity httpSecurity) {
		
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.httpBasic(basic -> basic.disable())
			.formLogin(form -> form.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		httpSecurity.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, ApiPathConstants.PublicApiRoutes.REGISTER_USER).permitAll()
				.requestMatchers(HttpMethod.POST, ApiPathConstants.PublicApiRoutes.USER_LOGIN).permitAll()
				.requestMatchers(HttpMethod.POST, ApiPathConstants.PublicApiRoutes.REFRESH_TOKEN).permitAll()
				.anyRequest().authenticated());
		
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return username -> {
	        throw new UsernameNotFoundException("JWT-based authentication only");
	    };
	}
}
