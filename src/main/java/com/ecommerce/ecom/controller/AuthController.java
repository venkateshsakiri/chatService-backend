package com.ecommerce.ecom.controller;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecom.dto.AuthenticationRequest;
import com.ecommerce.ecom.dto.SignupRequest;
import com.ecommerce.ecom.dto.UserDto;
import com.ecommerce.ecom.entity.User;
import com.ecommerce.ecom.repository.userRepository;
import com.ecommerce.ecom.service.AuthService;
import com.ecommerce.ecom.utils.JwtUtil;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	public AuthenticationManager authenticationManager;
	
	public UserDetailsService userDetailService;

	public userRepository userRepo;
	
	public JwtUtil jwtUtils;
	
	public static String TOKEN_PREFIX ="Bearer";
	
	public static String HEADER_STRING ="Authorization";
	
	public AuthService authService;
	
	@PostMapping("/authenticate")
	public void creatAuthenticationToken(@RequestBody AuthenticationRequest authenticationReqest, HttpServletResponse response) throws IOException , JSONException, java.io.IOException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReqest.userName, authenticationReqest.password));
		}
		catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
		
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationReqest.userName);
		Optional<User> optionaluser  = userRepo.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtils.generateToken(userDetails.getUsername());
		
		if(optionaluser.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId",optionaluser.get().id)
					.put("role", optionaluser.get().role)
					.toString());
			
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		if(authService.hasUserWithEmail(signupRequest.email)) {
			return new ResponseEntity<>("User already exists",HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto userDto = authService.createUser(signupRequest);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
}
