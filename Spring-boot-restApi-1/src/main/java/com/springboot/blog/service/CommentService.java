package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import java.util.*;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentdto);
	
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	public CommentDto getCommentsById(long postId,long commentId);
	
	public CommentDto updateComment(long postId,long commentId,CommentDto commentDto);
	
	public void deleteCommentById(long postId,long commentId);
}
