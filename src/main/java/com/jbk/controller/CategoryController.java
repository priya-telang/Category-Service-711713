package com.jbk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.exception.ResourceNotExistsException;
import com.jbk.model.CategoryModel;

import com.jbk.service.CategoryService;


import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@RestController
@RequestMapping("category")
public class CategoryController {

	
	@Autowired
	private CategoryService service;
	
	//add category
	@PostMapping("add-category")
	public ResponseEntity<String> addCategory(@RequestBody @Valid CategoryModel categoryModel){
		
		 service.addCategory(categoryModel);
	
		
			return ResponseEntity.ok("Category Added !!");
	
	
	}
	@GetMapping("get-category-by-id/{categoryId}")
	public ResponseEntity<CategoryModel> getCategoryById(@PathVariable long categoryId){
		
		CategoryModel categoryModel = service.getCategoryById(categoryId);
		
		return ResponseEntity.ok(categoryModel);
		
	}
	@PutMapping("update-category")
	public ResponseEntity<String> updateCategory(@RequestBody @Valid CategoryModel categoryModel){
     
		service.updateCategory(categoryModel);
		
		return  ResponseEntity.ok("Category Update !!");
		
	}
}
	
