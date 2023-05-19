package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
		description = "Post Dto Model Information"
		)
public class PostDto {
	private long id;
	@Schema(
			description = "Blog Post Title"
			)
	@NotEmpty
	@Size(min=2, message= "Post title should have at least 2 charecters")
	private String title;
	@Schema(
			description = "Blog Post Descrip[tion"
			)
	@NotEmpty
	@Size(min=10, message= "Post discription should have at least 10 charecters")
	private String description;
	
	@Schema(
			description = "Blog Post Content"
			)
	@NotEmpty
	private String content;
	
	
	private Set<CommentDto> comments;
	@Schema(
			description = "Blog Post Category"
			)
	private long categoryId;
}
