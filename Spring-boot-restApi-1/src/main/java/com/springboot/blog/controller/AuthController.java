package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(
		name = "REst Api Authentication "
		)
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	//Build login rest api
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JWTAuthResponse> login( @RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
	}
	
	//Build Register build restApi
	@PostMapping(value = {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String register = authService.register(registerDto);
		return new ResponseEntity<String>(register,HttpStatus.CREATED);
	}
	
	

}
