package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/api/b1/posts")
@Tag(
	name = "CRUD Rest API for POST Resource "
	)
public class PostResource {

	@Autowired
	private PostService postService;
	
	//create blog post restapi

	@Operation(summary = "create Post Rest API",
			description = "crete Post Rest APi to save Database")
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 Created"
			)
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	//http://localhost:8088/api/b1/posts?pageNum=0&pageSize=5&sortBy=title&sortDir=asc
	@Operation(summary ="Featch all Post Rest API",
			description = "Get all Post Rest APi ")
	@GetMapping
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNum",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int pageNum,
			@RequestParam(value="pagSizee",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir){
		return new ResponseEntity<>(postService.getAllPost(pageNum,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	//build get posts by category rest api
	@Operation(summary = "Get Post By category Id Rest API",
			description = "Get Post based on category id Rest APi")
	@GetMapping("/category/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable(name="id") long categoryId){
		return ResponseEntity.ok(postService.getPostByCategory(categoryId));
	}
	
	
	@Operation(summary = "Get Post By Id Rest API",
			description = "Get Single Post Rest APi from Database")
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@Operation(summary = "Update Post Rest API",
			description = "Update Post in  Database")
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
		PostDto updatePost = postService.updatePost(postDto, id);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Post Rest API",
			description = "Delete Post Rest APi from Database")
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<String>("Post Entity deleted succesfully",HttpStatus.OK);
	}
	
}
