package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
