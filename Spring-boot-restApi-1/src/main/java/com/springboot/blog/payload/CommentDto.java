package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

	private long id;
	@NotEmpty
	@Size(min=2, message= "Name should have at least 2 charecters")
	private String name;
	
	@NotEmpty(message="Email Should not null or empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=10, message= "Comment body should have at least 10 charecters")
	private String body;
}
