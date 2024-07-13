package com.ecommerce.ecom.dto;

import com.ecommerce.ecom.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	public Long id;
	
	public String email;
	
	public String password;
	
	public UserRole userRole;
}
