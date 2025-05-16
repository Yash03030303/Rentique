package com.capgemini.equipment_rental.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.services.CategoriesService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PostMapping
    public ResponseEntity<Categories> createCategory(@Valid @RequestBody Categories category, BindingResult result) {
        log.info("Received request to create category: {}", category);

        if (result.hasErrors()) {
            log.warn("Validation failed for category creation: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        Categories createdCategory = categoriesService.createCategory(category);
        log.info("Category created successfully with ID: {}", createdCategory.getCategoryId());

        return ResponseEntity.created(URI.create("/api/categories/" + createdCategory.getCategoryId()))
                .body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Categories>> getAllCategories() {
        log.info("Received request to fetch all categories");
        List<Categories> categories = categoriesService.getAllCategories();
        log.debug("Number of categories fetched: {}", categories.size());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        log.info("Fetching category with ID: {}", id);
        Categories category = categoriesService.getCategoryById(id);
        log.debug("Category fetched: {}", category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable Long id,
                                                     @Valid @RequestBody Categories updatedCategory,
                                                     BindingResult result) {
        log.info("Received request to update category with ID: {}", id);

        if (result.hasErrors()) {
            log.warn("Validation failed while updating category: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        Categories category = categoriesService.updateCategory(id, updatedCategory);
        log.info("Category with ID {} updated successfully", id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("Received request to delete category with ID: {}", id);
        categoriesService.deleteCategory(id);
        log.info("Category with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
