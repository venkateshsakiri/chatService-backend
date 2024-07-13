package com.ecommerce.ecom.config;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.ecom.filters.JwtRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

	public JwtRequestFilter filter;
	
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers("/sign-up","/authenticate","/order/**")
				.permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/api/**")
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
//		return httpSecurity
//				.authorizeRequests()
//				  .antMatchers("/login*")
//				  .permitAll()
//				  .anyRequest()
//				  .authenticated()
//				  .and()
//				  .formLogin()				
//		          .successHandler(appAuthenticationSuccessHandler());
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	
	
	
	
//	not required below 2 methods
	public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	    protected void handle(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {
	    }

	}
	
	@Bean
	public AuthenticationSuccessHandler appAuthenticationSuccessHandler(){
	     return new AppAuthenticationSuccessHandler();

	}
}
