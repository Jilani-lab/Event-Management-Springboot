package com.springboot.blog.service.Impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Category;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;



@Service
public class PostServiceImpl implements PostService {

	
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	private CategoryRepository categoryRepository;
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}

	private PostDto mapToDto(Post post) {
		
		PostDto postDto=mapper.map(post, PostDto.class);
//		PostDto postdto=PostDto.builder()
//				.id(post.getId())
//				.title(post.getTitle())
//				.description(post.getDescription())
//				.content(post.getContent())
//				.build();
		return postDto;
	}
	
	private Post mapToPost(PostDto postdto) {
		Post post=mapper.map(postdto, Post.class);
//		Post post=Post.builder()
//				.id(postdto.getId())
//				.title(postdto.getTitle())
//				.description(postdto.getDescription())
//				.content(postdto.getContent())
//				.build();
		return post;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
		.orElseThrow(()-> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		//convert dto to model
		Post post= mapToPost(postDto);
		post.setCategory(category);
		Post newpost = postRepository.save(post);
		//convert entity into DTO
		PostDto newpostdto=mapToDto(newpost);
		return newpostdto;
	}
	@Override
	public PostResponse getAllPost(int pageNum,int pageSize, String sortBy, String sortDir) {
		
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				:Sort.by(sortBy).descending();
				
		Pageable pageable= PageRequest.of(pageNum, pageSize,sort);
		
		// TODO Auto-generated method stub
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		//get content from page object
		List<Post> listOfPost=posts.getContent();
		
		List<PostDto> listofposts = listOfPost.stream().map(post ->mapToDto(post)).collect(Collectors.toList());
		
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(listofposts);
		postResponse.setPageNum(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {
		// TODO Auto-generated method stub
		
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto,long id) {
		// TODO Get post by id from database
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		post.setCategory(category);
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatePost= postRepository.save(post);
		return mapToDto(updatePost);
		
		
	}

	@Override
	public void deletePostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		
	}

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		return posts.stream().map((post)->mapToDto(post)).collect(Collectors.toList());
	}

}
