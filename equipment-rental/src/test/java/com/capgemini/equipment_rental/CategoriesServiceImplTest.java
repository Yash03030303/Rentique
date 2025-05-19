package com.capgemini.equipment_rental;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.exceptions.CategoryNotFoundException;
import com.capgemini.equipment_rental.repositories.CategoriesRepository;
import com.capgemini.equipment_rental.services.CategoriesServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoriesServiceImplTest {

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    private Categories testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Categories();
        testCategory.setCategoryId(1L);
        testCategory.setName("Test Category");
    }

    @Test
    void createCategory_Success() {
        when(categoriesRepository.save(any(Categories.class))).thenReturn(testCategory);

        Categories createdCategory = categoriesService.createCategory(testCategory);

        assertNotNull(createdCategory);
        assertEquals("Test Category", createdCategory.getName());
        verify(categoriesRepository, times(1)).save(testCategory);
    }

    @Test
    void getCategoryById_Found() {
        when(categoriesRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        Categories foundCategory = categoriesService.getCategoryById(1L);

        assertNotNull(foundCategory);
        assertEquals(1L, foundCategory.getCategoryId());
        verify(categoriesRepository, times(1)).findById(1L);
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoriesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () ->
            categoriesService.getCategoryById(1L)
        );
        verify(categoriesRepository, times(1)).findById(1L);
    }

    @Test
    void getAllCategories_Success() {
        List<Categories> categoriesList = Arrays.asList(testCategory);
        when(categoriesRepository.findAll()).thenReturn(categoriesList);

        List<Categories> result = categoriesService.getAllCategories();

        assertEquals(1, result.size());
        verify(categoriesRepository, times(1)).findAll();
    }

    @Test
    void updateCategory_Success() {
        Categories updatedCategory = new Categories();
        updatedCategory.setName("Updated Category");

        when(categoriesRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoriesRepository.save(any(Categories.class))).thenReturn(updatedCategory);

        Categories result = categoriesService.updateCategory(1L, updatedCategory);

        assertEquals("Updated Category", result.getName());
        verify(categoriesRepository, times(1)).save(any(Categories.class));
    }

    @Test
    void deleteCategory_Success() {
        when(categoriesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoriesRepository).deleteById(1L);

        categoriesService.deleteCategory(1L);

        verify(categoriesRepository, times(1)).existsById(1L);
        verify(categoriesRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoriesRepository.existsById(1L)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () ->
            categoriesService.deleteCategory(1L)
        );
        verify(categoriesRepository, times(1)).existsById(1L);
        verify(categoriesRepository, never()).deleteById(1L);
    }
}