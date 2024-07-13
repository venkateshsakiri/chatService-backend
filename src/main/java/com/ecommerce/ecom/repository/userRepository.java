package com.ecommerce.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecom.entity.User;
import com.ecommerce.ecom.enums.UserRole;

@Repository
public interface userRepository extends JpaRepository<User, Long>{

	Optional<User> findFirstByEmail(String email);
	
	User findByRole(UserRole userRole);

}
