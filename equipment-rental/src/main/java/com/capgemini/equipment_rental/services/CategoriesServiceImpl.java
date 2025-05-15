package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.exceptions.CategoryNotFoundException;
import com.capgemini.equipment_rental.repositories.CategoriesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public Categories createCategory(Categories category) {
        log.info("Creating new category: {}", category.getName());
        return categoriesRepository.save(category);
    }

    @Override
    public Categories getCategoryById(Long categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        return categoriesRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", categoryId);
                    return new CategoryNotFoundException("Category with ID " + categoryId + " not found.");
                });
    }

    @Override
    public List<Categories> getAllCategories() {
        log.info("Fetching all categories from repository");
        List<Categories> categoriesList = categoriesRepository.findAll();
        log.debug("Total categories found: {}", categoriesList.size());
        return categoriesList;
    }

    @Override
    public Categories updateCategory(Long categoryId, Categories updatedCategory) {
        log.info("Updating category with ID: {}", categoryId);
        Categories existingCategory = getCategoryById(categoryId);

        existingCategory.setName(updatedCategory.getName());

        Categories savedCategory = categoriesRepository.save(existingCategory);
        log.info("Category with ID {} updated successfully", categoryId);
        return savedCategory;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        log.info("Attempting to delete category with ID: {}", categoryId);
        if (!categoriesRepository.existsById(categoryId)) {
            log.warn("Category not found with ID: {}", categoryId);
            throw new CategoryNotFoundException("Category with ID " + categoryId + " not found.");
        }
        categoriesRepository.deleteById(categoryId);
        log.info("Category with ID {} deleted successfully", categoryId);
    }
}
