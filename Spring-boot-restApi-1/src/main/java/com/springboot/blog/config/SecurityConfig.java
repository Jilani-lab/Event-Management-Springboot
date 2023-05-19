package com.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.websocket.Session;

@Configuration
@EnableMethodSecurity
@SecurityScheme(name = "Bear Authentication",
				type = SecuritySchemeType.HTTP,
				bearerFormat = "JWT",
				scheme = "bearer")
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService detailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//		http.csrf().disable().authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated())
//		.httpBasic(Customizer.withDefaults());
		
		http.csrf().disable().authorizeHttpRequests((authorize)-> 
						authorize.requestMatchers(HttpMethod.GET,"/api/b1/**").permitAll()
								.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
								.requestMatchers("/swagger-ui/**").permitAll()
								 .requestMatchers("/v3/api-docs/**").permitAll()
								   .requestMatchers("/api/auth/**").permitAll()
										.anyRequest().authenticated())
									.exceptionHandling(exception-> exception
												.authenticationEntryPoint(authenticationEntryPoint))
									.sessionManagement(session -> session
													.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(){
//		UserDetails  jilani=User.builder()
//				.username("jilani")
//				.password(passwordEncoder().encode("Jio@123"))
//				.roles("USER").build();
//		
//		UserDetails admin=User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(jilani,admin);
//	}
}
