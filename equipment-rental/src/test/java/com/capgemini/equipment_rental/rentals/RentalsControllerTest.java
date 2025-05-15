package com.capgemini.equipment_rental.rentals;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import com.capgemini.equipment_rental.controllers.RentalsController;
import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.services.RentalsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RentalsController.class)
class RentalsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RentalsService rentalsService;

	private Rentals sampleRental;

	@BeforeEach
	void setUp() {
		sampleRental = new Rentals();
		sampleRental.setRentalId(1L);
		sampleRental.setRentalDate(LocalDate.now());
		sampleRental.setDueDate(LocalDate.now().plusDays(2));
		sampleRental.setTotalAmount(new BigDecimal("100.00"));
	}

	@Test
	void getAllRentals_returnsOkAndList() throws Exception {
		when(rentalsService.getAllRentals()).thenReturn(Arrays.asList(sampleRental));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/rentals")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].rentalId").value(1L));
	}

	@Test
	void getRentalsById_returnsOkAndRental() throws Exception {
		when(rentalsService.getRentalsById(1L)).thenReturn(sampleRental);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/rentals/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.rentalId").value(1L));
	}

	@Test
	void createRentals_returnsCreatedAndRental() throws Exception {
		when(rentalsService.createRentals(any(Rentals.class))).thenReturn(sampleRental);
		String json = """
				{
				    "rentalDate": "%s",
				    "dueDate": "%s",
				    "totalAmount": 100.00
				}
				""".formatted(LocalDate.now(), LocalDate.now().plusDays(2));
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/rentals").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.rentalId").value(1L));
	}

	@Test
	void updateRentals_returnsOkAndRental() throws Exception {
		when(rentalsService.updateRentals(eq(1L), any(Rentals.class))).thenReturn(sampleRental);
		String json = """
				{
				    "rentalDate": "%s",
				    "dueDate": "%s",
				    "totalAmount": 100.00
				}
				""".formatted(LocalDate.now(), LocalDate.now().plusDays(2));
		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/rentals/1").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.rentalId").value(1L));
	}

	@Test
	void deleteRentals_returnsNoContent() throws Exception {
		doNothing().when(rentalsService).deleteRentals(1L);
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/rentals/1")).andExpect(status().isNoContent());
	}
}
