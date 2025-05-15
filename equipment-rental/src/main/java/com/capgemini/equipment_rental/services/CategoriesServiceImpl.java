package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.exceptions.CategoryNotFoundException;
import com.capgemini.equipment_rental.repositories.CategoriesRepository;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	private CategoriesRepository categoriesRepository;

	@Autowired
	public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
		super();
		this.categoriesRepository = categoriesRepository;
	}

	@Override
	public Categories createCategory(Categories category) {
		return categoriesRepository.save(category);
	}

	@Override
	public Categories getCategoryById(Long categoryId) {
		return categoriesRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found."));
	}

	@Override
	public List<Categories> getAllCategories() {
		return categoriesRepository.findAll();
	}

	@Override
	public Categories updateCategory(Long categoryId, Categories updatedCategory) {
		Categories existingCategory = getCategoryById(categoryId);

		existingCategory.setName(updatedCategory.getName());
		//existingCategory.setEquipmentList(updatedCategory.getEquipmentList());

		return categoriesRepository.save(existingCategory);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		if (!categoriesRepository.existsById(categoryId)) {
			throw new CategoryNotFoundException("Category with ID " + categoryId + " not found.");
		}
		categoriesRepository.deleteById(categoryId);
	}
}
