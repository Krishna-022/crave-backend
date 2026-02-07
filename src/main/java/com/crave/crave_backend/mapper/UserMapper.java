package com.crave.crave_backend.mapper;

import com.crave.crave_backend.dto.in.RegisterUserInDto;
import com.crave.crave_backend.entity.User;

public final class UserMapper {
	
	public static User toEntity(RegisterUserInDto registerUserInDto) {
		User user = new User();
		user.setContactNumber(registerUserInDto.getContactNumber());
		user.setEmail(registerUserInDto.getEmail());
		user.setFirstName(registerUserInDto.getFirstName());
		user.setLastName(registerUserInDto.getLastName());
		user.setMiddleName(registerUserInDto.getMiddleName());
		return user;
	}
}
