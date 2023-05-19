package com.springboot.blog.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;
@RestController
@RequestMapping("/api/categories")
@Tag(
		name = "CRUD Rest API for Category Manegement "
	)
public class CategoryController {
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//add category rest api
	@Operation(summary = "Create Category for Rest API")
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto addCategory = categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(addCategory,HttpStatus.CREATED);
	}
	
	//get Category rest api
	@Operation(summary = "Get Category By Id in Rest API")
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	//get all Categor
	@Operation(summary = "Get All Category for Rest API")
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		return ResponseEntity.ok(allCategory);
	}
	
	//bulid update category Rest Api
	@Operation(summary = "Update Category for Rest API")
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") Long categoryId){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
	}
	
	//build delete category rest Api
	@Operation(summary = "Delete Category for Rest API")
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		return ResponseEntity.ok("Category deleted Succesfully");
	}

}
