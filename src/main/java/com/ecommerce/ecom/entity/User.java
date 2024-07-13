package com.ecommerce.ecom.entity;

import com.ecommerce.ecom.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String email;
	
	public String password;
	
	public String name;
	
	public UserRole role;
	
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] img;
}
