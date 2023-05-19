package com.springboot.blog;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


@SpringBootApplication
@OpenAPIDefinition(
		info= @Info(title = "Spring Boot Blog App Rest Api",
		description = "Spring Boot Blog App Rest API Documentation",
		version = "v1.0",
		contact = @Contact(name = "Jilani",email="jilani@gmail.com",url = "https://www.jilani.com"),
		license = @License(name = "Apache 2.0",url = "www.google.com")),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentations",
				url="www.github.com")
		)
public class SpringBootRestApi1Application {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApi1Application.class, args);
	}

}
