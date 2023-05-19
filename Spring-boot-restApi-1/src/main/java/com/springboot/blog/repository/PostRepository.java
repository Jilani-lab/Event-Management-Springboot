package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.springframework.stereotype.Repository;

import com.springboot.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findByCategoryId(Long categoryId);
	
}
