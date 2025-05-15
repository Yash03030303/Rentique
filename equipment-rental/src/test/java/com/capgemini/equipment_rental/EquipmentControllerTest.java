package com.capgemini.equipment_rental;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.capgemini.equipment_rental.controllers.EquipmentController;
import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.services.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EquipmentController.class)
class EquipmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EquipmentService equipmentService;

	private Equipment equipment;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		Categories category = new Categories();
		category.setCategoryId(1L);

		equipment = new Equipment(1L, "Hammer", new BigDecimal("15.00"), 10L, category);
		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetAllEquipment() throws Exception {
		when(equipmentService.getAllEquipment()).thenReturn(List.of(equipment));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Hammer"));
	}

	@Test
	void testGetEquipmentById() throws Exception {
		when(equipmentService.getEquipmentById(1L)).thenReturn(equipment);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Hammer"));
	}

	@Test
	void testCreateEquipment() throws Exception {
		// Create request body as a map (matches your Postman JSON)
		String requestBody = """
				{
				    "name": "Hammer",
				    "rentalPricePerDay": 15.00,
				    "stock": 10,
				    "categories": {
				        "categoryId": 1
				    }
				}
				""";

		when(equipmentService.createEquipment(any(Equipment.class))).thenReturn(equipment);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/equipment").contentType(APPLICATION_JSON).content(requestBody))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Hammer"))
				.andExpect(jsonPath("$.rentalPricePerDay").value(15.00)).andExpect(jsonPath("$.stock").value(10));
	}

	@Test
	void testUpdateEquipment() throws Exception {
		// Create request body matching expected JSON
		String requestBody = """
				{
				    "name": "Hammer",
				    "rentalPricePerDay": 15.00,
				    "stock": 10,
				    "categories": {
				        "categoryId": 1
				    }
				}
				""";

		when(equipmentService.updateEquipment(eq(1L), any(Equipment.class))).thenReturn(equipment);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/equipment/1").contentType(APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Hammer"))
				.andExpect(jsonPath("$.rentalPricePerDay").value(15.00)).andExpect(jsonPath("$.stock").value(10));
	}

	@Test
	void testDeleteEquipment() throws Exception {
		doNothing().when(equipmentService).deleteEquipment(1L);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/equipment/1")).andExpect(status().isNoContent());
	}
}
