package com.springboot.blog.payload;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private int pageNum;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
}