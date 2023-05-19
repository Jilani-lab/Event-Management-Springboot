package com.springboot.blog.service.Impl;

import java.util.List;
import java.util.Optional;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper mapper;

	/**
	 * @param categoryRepository
	 * @param mapper
	 */
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}


	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category=mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return mapper.map(savedCategory, CategoryDto.class);
	}


	@Override
	public CategoryDto getCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId)
		.orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		return mapper.map(category, CategoryDto.class);
	}


	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map((category)->mapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
	}


	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		category.setId(categoryId);
		category.setDecription(categoryDto.getDecription());
		category.setName(categoryDto.getName());
		
		Category updatedCategory = categoryRepository.save(category);
				
		return mapper.map(updatedCategory, CategoryDto.class);
	}


	@Override
	public void deleteCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		categoryRepository.delete(category);
	}


	

}
