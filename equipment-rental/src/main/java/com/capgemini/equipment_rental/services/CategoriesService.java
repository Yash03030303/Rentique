package com.capgemini.equipment_rental.services;

import java.util.List;



import com.capgemini.equipment_rental.entity.Categories;

public interface CategoriesService {
    Categories createCategory(Categories category);

    Categories getCategoryById(Long categoryId);

    List<Categories> getAllCategories();

    Categories updateCategory(Long categoryId, Categories category);

    void deleteCategory(Long categoryId);
}
