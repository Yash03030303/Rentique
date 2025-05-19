package com.capgemini.equipment_rental;




import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.equipment_rental.controllers.CategoriesController;
import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.services.CategoriesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CategoriesControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CategoriesService categoriesService;

    @InjectMocks
    private CategoriesController categoriesController;

    private Categories testCategory;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriesController).build();
        testCategory = new Categories();
        testCategory.setCategoryId(1L);
        testCategory.setName("Test Category");
    }

    @Test
    void createCategory_Success() throws Exception {
        when(categoriesService.createCategory(any(Categories.class))).thenReturn(testCategory);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/categories/1"))
                .andExpect(jsonPath("$.categoryId").value(1));

        verify(categoriesService, times(1)).createCategory(any(Categories.class));
    }

    @Test
    void getAllCategories_Success() throws Exception {
        when(categoriesService.getAllCategories()).thenReturn(Collections.singletonList(testCategory));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryId").value(1));

        verify(categoriesService, times(1)).getAllCategories();
    }

    @Test
    void getCategoryById_Success() throws Exception {
        when(categoriesService.getCategoryById(1L)).thenReturn(testCategory);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1));

        verify(categoriesService, times(1)).getCategoryById(1L);
    }

    @Test
    void updateCategory_Success() throws Exception {
        Categories updated = new Categories();
        updated.setName("Updated");

        when(categoriesService.updateCategory(eq(1L), any(Categories.class))).thenReturn(updated);

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));

        verify(categoriesService, times(1)).updateCategory(eq(1L), any(Categories.class));
    }

    @Test
    void deleteCategory_Success() throws Exception {
        doNothing().when(categoriesService).deleteCategory(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoriesService, times(1)).deleteCategory(1L);
    }

}