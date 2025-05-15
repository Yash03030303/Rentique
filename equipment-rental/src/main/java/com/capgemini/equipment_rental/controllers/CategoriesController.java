package com.capgemini.equipment_rental.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.services.CategoriesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

	private final CategoriesService categoriesService;

	@Autowired
	public CategoriesController(CategoriesService categoriesService) {
		this.categoriesService = categoriesService;
	}

	// Create a new category
	@PostMapping
	public ResponseEntity<Categories> createCategory(@Valid @RequestBody Categories category,BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid category data: " + result.getAllErrors());
		}
		Categories createdCategory = categoriesService.createCategory(category);
		return ResponseEntity.created(URI.create("/api/categories/" + createdCategory.getCategoryId()))
				.body(createdCategory);
	}

	// Get all categories
	@GetMapping
	public ResponseEntity<List<Categories>> getAllCategories() {
		List<Categories> categories = categoriesService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

	// Get a category by ID
	@GetMapping("/{id}")
	public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
		Categories category = categoriesService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}

	// Update a category
	@PutMapping("/{id}")
	public ResponseEntity<Categories> updateCategory(@PathVariable Long id,
			@Valid @RequestBody Categories updatedCategory,BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid category data: " + result.getAllErrors());
		}
		Categories category = categoriesService.updateCategory(id, updatedCategory);
		return ResponseEntity.ok(category);
	}

	// Delete a category
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoriesService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}
