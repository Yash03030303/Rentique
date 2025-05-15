package com.capgemini.equipment_rental.controllers;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.services.RentalItemsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(RentalItemsController.class)
class RentalItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalItemsService rentalItemsService;

    private RentalItems rentalItem;
    private ObjectMapper objectMapper;

//    @BeforeEach
//    void setUp() {
//        rentalItem = new RentalItems();
//        rentalItem.setRentalItemId(1L);
//        rentalItem.setQuantity(3L);
//        rentalItem.setDaysRented(4L);
//
//        objectMapper = new ObjectMapper();
//    }
    
    @BeforeEach
    void setUp() {
        Rentals rental = new Rentals();
        rental.setRentalId(1L);

        Equipment equipment = new Equipment();
        equipment.setEquipmentId(1L);

        rentalItem = new RentalItems();
        rentalItem.setRentalItemId(1L);
        rentalItem.setQuantity(3L);
        rentalItem.setDaysRented(4L);
        rentalItem.setRental(rental);
        rentalItem.setEquipment(equipment);

        objectMapper = new ObjectMapper();
    }
    @Test
    void testCreateRentalItem() throws Exception {
        String requestBody = """
            {
                "rentalItemId": 1,
                "quantity": 3,
                "daysRented": 4,
                "rental": {
                    "rentalId": 1
                },
                "equipment": {
                    "equipmentId": 1
                }
            }
            """;

        when(rentalItemsService.createRentalItem(any())).thenReturn(rentalItem);

        mockMvc.perform(post("/api/rental-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rentalItemId").value(1));
    }





    @Test
    void testGetAllRentalItems() throws Exception {
        when(rentalItemsService.getAllRentalItems()).thenReturn(Arrays.asList(rentalItem));

        mockMvc.perform(get("/api/rental-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(3));
    }

    @Test
    void testGetRentalItemById() throws Exception {
        when(rentalItemsService.getRentalItemById(1L)).thenReturn(rentalItem);

        mockMvc.perform(get("/api/rental-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.daysRented").value(4));
    }

//    @Test
//    void testUpdateRentalItem() throws Exception {
//        rentalItem.setQuantity(10L);
//        when(rentalItemsService.updateRentalItem(eq(1L), any())).thenReturn(rentalItem);
//
//        mockMvc.perform(put("/api/rental-items/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(rentalItem)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.quantity").value(10));
//    }
    
    @Test
    void testUpdateRentalItem() throws Exception {
        // Mocked response object
        rentalItem.setQuantity(10L);

        // JSON request matching expected structure
        String requestBody = """
            {
                "rentalItemId": 1,
                "quantity": 10,
                "daysRented": 4,
                "rental": {
                    "rentalId": 1
                },
                "equipment": {
                    "equipmentId": 1
                }
            }
            """;

        when(rentalItemsService.updateRentalItem(eq(1L), any())).thenReturn(rentalItem);

        mockMvc.perform(put("/api/rental-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10));
    }


    @Test
    void testDeleteRentalItem() throws Exception {
        doNothing().when(rentalItemsService).deleteRentalItem(1L);

        mockMvc.perform(delete("/api/rental-items/1"))
                .andExpect(status().isNoContent());
    }
}
