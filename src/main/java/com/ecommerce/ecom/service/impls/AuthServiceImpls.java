package com.ecommerce.ecom.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.dto.SignupRequest;
import com.ecommerce.ecom.dto.UserDto;
import com.ecommerce.ecom.entity.User;
import com.ecommerce.ecom.enums.UserRole;
import com.ecommerce.ecom.repository.userRepository;
import com.ecommerce.ecom.service.AuthService;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpls implements AuthService{
	
	@Autowired
	public userRepository userRepo;
	
	@Autowired(required = false)
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public UserDto createUser(SignupRequest request) {
		
		User user = new User();
		
		user.email = request.email;
		user.name = request.name;
		user.password = new BCryptPasswordEncoder().encode(request.password);
		user.role = UserRole.CUSTOMER;
		
		User createdUser = userRepo.save(user);
		
		UserDto dto = new UserDto();
		dto.id = createdUser.id;
		return dto;
		
		
	}
	
	public Boolean hasUserWithEmail(String email) {
		return userRepo.findFirstByEmail(email).isPresent();
	}
	
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepo.findByRole(UserRole.ADMIN);
		if(null == adminAccount) {
			User user = new User();
			user.email = "admin@test.com";
			user.password = new BCryptPasswordEncoder().encode("admin");
			user.name = "admin";
			user.role = UserRole.ADMIN;
			userRepo.save(user);
		}
	}

}
