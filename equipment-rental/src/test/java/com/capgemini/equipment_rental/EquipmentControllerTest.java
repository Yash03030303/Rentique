package com.capgemini.equipment_rental;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.equipment_rental.controllers.EquipmentController;
import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.services.EquipmentService;

class EquipmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EquipmentService equipmentService;

    @InjectMocks
    private EquipmentController equipmentController;

    private Equipment sampleEquipment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(equipmentController).build();

        Categories category = new Categories();
        category.setCategoryId(1L);

        sampleEquipment = new Equipment();
        sampleEquipment.setEquipmentId(1L);
        sampleEquipment.setName("Hammer");
        sampleEquipment.setRentalPricePerDay(new BigDecimal("15.00"));
        sampleEquipment.setStock(10L);
        sampleEquipment.setCategories(category);
    }

    @Test
    void getAllEquipment_returnsOkAndList() throws Exception {
        when(equipmentService.getAllEquipment()).thenReturn(Arrays.asList(sampleEquipment));
        mockMvc.perform(get("/api/equipment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].equipmentId").value(1L));
    }

    @Test
    void getEquipmentById_returnsOkAndEquipment() throws Exception {
        when(equipmentService.getEquipmentById(1L)).thenReturn(sampleEquipment);
        mockMvc.perform(get("/api/equipment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value(1L));
    }

    @Test
    void createEquipment_returnsCreatedAndEquipment() throws Exception {
        when(equipmentService.createEquipment(any(Equipment.class))).thenReturn(sampleEquipment);
        String json = """
                {
                  "name": "Hammer",
                  "rentalPricePerDay": 15.00,
                  "stock": 10,
                  "categories": {
                    "categoryId": 1
                  }
                }
                """;
        mockMvc.perform(post("/api/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.equipmentId").value(1L));
    }

    @Test
    void updateEquipment_returnsOkAndUpdatedEquipment() throws Exception {
        when(equipmentService.updateEquipment(eq(1L), any(Equipment.class))).thenReturn(sampleEquipment);
        String json = """
                {
                  "name": "Hammer",
                  "rentalPricePerDay": 20.00,
                  "stock": 15,
                  "categories": {
                    "categoryId": 1
                  }
                }
                """;
        mockMvc.perform(put("/api/equipment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value(1L));
    }

    @Test
    void deleteEquipment_returnsNoContent() throws Exception {
        doNothing().when(equipmentService).deleteEquipment(1L);
        mockMvc.perform(delete("/api/equipment/1"))
                .andExpect(status().isNoContent());
    }
}
