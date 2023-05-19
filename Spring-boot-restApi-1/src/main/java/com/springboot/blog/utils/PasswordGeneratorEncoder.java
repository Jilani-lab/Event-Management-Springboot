package com.springboot.blog.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
	
	public static void main(String[] args) {
		PasswordEncoder encoder=new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		System.out.println(encoder.encode("Jio@123"));
		System.out.println(encoder.encode("ramesh"));
		
	}

}
