package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/api/b1/")
@Tag(
		name = "CRUD Rest API for Comments Resource "
	)
public class CommentResource {
	
	@Autowired
	private CommentService commentService;
	
	@Operation(summary = "create Comments Rest API")
	@PostMapping("/posts/{id}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="id") long postId, 
			@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@Operation(summary = "Get comments by Post Id Rest API")
	@GetMapping("/posts/{id}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable(value="id")long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@Operation(summary = "Get comments by Post Id and comments ID Rest API")
	@GetMapping("/posts/{id}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommnetsById(@PathVariable(value="is")long postId,
			@PathVariable(value="commentId")long commentId){
		return new ResponseEntity<CommentDto>(commentService.getCommentsById(postId, commentId),HttpStatus.OK);
	}

	@Operation(summary = "Update comments by Post Id and comments ID Rest API")
	@PutMapping("/posts/{id}/comments/{commentid}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") long postId,
			@PathVariable(value="commentid") Long commentId,@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<CommentDto>(commentService.updateComment(commentId, postId, commentDto),HttpStatus.OK);
		
	}
	
	@Operation(summary = "Delete comments by Post Id and comments ID Rest API")
	@DeleteMapping("/posts/{id}/comments/{commentid}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="id") long postId,
			@PathVariable(value="commentid") long commentId){
		commentService.deleteCommentById(postId, commentId);
		return new ResponseEntity<String>("Comment deleted succefully",HttpStatus.OK);
	}

}
