package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import java.util.*;
import com.springboot.blog.payload.PostResponse;

import java.util.*;

public interface PostService {

	public PostDto createPost(PostDto postDto);
	
	public PostResponse getAllPost(int pageNum,int pageSize,String sortBy,String sortDir);
	
	public PostDto getPostById(Long id);
	
	public PostDto updatePost(PostDto postDto, long id);
	
	public void deletePostById(long id);
	
	List<PostDto> getPostByCategory(Long categoryId);
}
