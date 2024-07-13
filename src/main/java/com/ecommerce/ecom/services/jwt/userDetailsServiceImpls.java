package com.ecommerce.ecom.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.entity.User;
import com.ecommerce.ecom.repository.userRepository;

@Service
public class userDetailsServiceImpls implements UserDetailsService{

   @Autowired
   public userRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findFirstByEmail(username);
		
		if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User name not found", null);
		return new org.springframework.security.core.userdetails.User(optionalUser.get().email,optionalUser.get().password,new ArrayList<>());
	}

}
