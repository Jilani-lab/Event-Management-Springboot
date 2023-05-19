package com.springboot.blog.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	private CommentDto mapToCommentDto(Comment comment) {
		CommentDto commentdto=mapper.map(comment, CommentDto.class);
//		CommentDto commentdto=CommentDto.builder()
//				.id(comment.getId())
//				.name(comment.getName())
//				.email(comment.getEmail())
//				.body(comment.getBody())
//				.build();
		return commentdto;
	}
	
	private Comment mapToComment(CommentDto commentdto) {
		Comment comment=mapper.map(commentdto, Comment.class);
//		Comment comment=Comment.builder()
//				.id(commentdto.getId())
//				.name(commentdto.getName())
//				.email(commentdto.getEmail())
//				.body(commentdto.getBody())
//				.build();
		return comment;
	}
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentdto) {
		// TODO Auto-generated method stub
		
		Comment comment= mapToComment(commentdto);
		
		//retrive post entity by id
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		comment.setPost(post);
		
		//save entity to Db
		Comment newComment = commentRepository.save(comment);
		return mapToCommentDto(newComment);
		
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// TODO Auto-generated method stub
		List<Comment> comment = commentRepository.findByPostId(postId);
		
		//Convert list of comment entities to list
		return comment.stream().map(comments -> mapToCommentDto(comments)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentsById(long postId, long commentId) {
		// TODO Auto-generated method stub
		//retrive post entity by id
		Post post=postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		//retrive Comment by id
		Comment comment=commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(comment.getPost().getId() != post.getId()) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment dost not belongs to post");
		}
		
		return mapToCommentDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		// TODO Auto-generated method stub
		Post post=postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment=commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(comment.getPost().getId() != post.getId()) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment dost not belongs to post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		return mapToCommentDto(updatedComment);
		
	}


	@Override
	public void deleteCommentById(long postId, long commentId) {
		// TODO Auto-generated method stub
		Post post=postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment=commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
//		if(!comment.getPost().getId().equals(post.getId())) {
//			throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment doesnot belong to post");
//		}
		if(comment.getPost().getId() != post.getId()) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment dost not belongs to post");
		}
		
		commentRepository.delete(comment);
		
	}
	

	

}
