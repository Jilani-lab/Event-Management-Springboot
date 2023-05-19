package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDto;
import java.util.*;

public interface CategoryService {
	CategoryDto addCategory(CategoryDto categoryDto);

	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> getAllCategory();
	
	CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	
	void deleteCategory(Long categoryId);
}
