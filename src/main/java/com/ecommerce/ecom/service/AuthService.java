package com.ecommerce.ecom.service;

import com.ecommerce.ecom.dto.SignupRequest;
import com.ecommerce.ecom.dto.UserDto;

public interface AuthService {
	UserDto createUser(SignupRequest request);
	 Boolean hasUserWithEmail(String email);
}
