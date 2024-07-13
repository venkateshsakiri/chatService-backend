package com.ecommerce.ecom.dto;

import lombok.Data;

@Data
public class SignupRequest {

	public String email;
	
	public String password;
	
	public String name;
}
