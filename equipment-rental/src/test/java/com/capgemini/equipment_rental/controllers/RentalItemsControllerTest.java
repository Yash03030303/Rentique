package com.capgemini.equipment_rental.controllers;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.services.RentalItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RentalItemsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RentalItemsService rentalItemsService;

    @InjectMocks
    private RentalItemsController rentalItemsController;

    private RentalItems sampleItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rentalItemsController).build();

        Rentals rental = new Rentals();
        rental.setRentalId(1L);

        Equipment equipment = new Equipment();
        equipment.setEquipmentId(1L);

        sampleItem = new RentalItems();
        sampleItem.setRentalItemId(1L);
        sampleItem.setQuantity(2L);
        sampleItem.setDaysRented(3L);
        sampleItem.setRental(rental);
        sampleItem.setEquipment(equipment);
    }

    @Test
    void createRentalItem_returnsCreatedAndItem() throws Exception {
        when(rentalItemsService.createRentalItem(any(RentalItems.class))).thenReturn(sampleItem);

        String json = """
                {
                    "quantity": 2,
                    "daysRented": 3,
                    "rental": { "rentalId": 1 },
                    "equipment": { "equipmentId": 1 }
                }
                """;

        mockMvc.perform(post("/api/rental-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rentalItemId").value(1));
    }

    @Test
    void getAllRentalItems_returnsOkAndList() throws Exception {
        when(rentalItemsService.getAllRentalItems()).thenReturn(Arrays.asList(sampleItem));

        mockMvc.perform(get("/api/rental-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rentalItemId").value(1));
    }

    @Test
    void getRentalItemById_returnsOkAndItem() throws Exception {
        when(rentalItemsService.getRentalItemById(1L)).thenReturn(sampleItem);

        mockMvc.perform(get("/api/rental-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentalItemId").value(1));
    }

    @Test
    void updateRentalItem_returnsOkAndUpdatedItem() throws Exception {
        when(rentalItemsService.updateRentalItem(eq(1L), any(RentalItems.class))).thenReturn(sampleItem);

        String json = """
                {
                    "quantity": 5,
                    "daysRented": 6,
                    "rental": { "rentalId": 1 },
                    "equipment": { "equipmentId": 1 }
                }
                """;

        mockMvc.perform(put("/api/rental-items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentalItemId").value(1));
    }

    @Test
    void deleteRentalItem_returnsNoContent() throws Exception {
        doNothing().when(rentalItemsService).deleteRentalItem(1L);

        mockMvc.perform(delete("/api/rental-items/1"))
                .andExpect(status().isNoContent());
    }
}
