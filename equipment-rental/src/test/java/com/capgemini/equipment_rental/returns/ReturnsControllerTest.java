package com.capgemini.equipment_rental.returns;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.capgemini.equipment_rental.controllers.ReturnsController;
import com.capgemini.equipment_rental.entity.Returns;
import com.capgemini.equipment_rental.services.RentalsService;
import com.capgemini.equipment_rental.services.ReturnsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class ReturnsControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ReturnsService returnsService;

	@Mock
	private RentalsService rentalsService;

	@InjectMocks
	private ReturnsController returnsController;

	private ObjectMapper objectMapper;
	private Returns testReturn;

	@BeforeEach
	void setUp() {
		// Configure ObjectMapper for proper date handling
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// Enhanced MockMvc setup with exception handling
		mockMvc = MockMvcBuilders.standaloneSetup(returnsController)
				.setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();

		testReturn = new Returns();
		testReturn.setReturnId(1L);
		testReturn.setReturnDate(LocalDate.now());
		testReturn.setItemCondition("Good");
		testReturn.setLateFee(BigDecimal.valueOf(0.0));
	}

	@Test
	void getAllReturns_Success() throws Exception {
		List<Returns> returnsList = Arrays.asList(testReturn);
		when(returnsService.getAllReturns()).thenReturn(returnsList);

		mockMvc.perform(get("/api/returns")).andExpect(status().isOk()).andExpect(jsonPath("$[0].returnId").value(1L));
		verify(returnsService, times(1)).getAllReturns();
	}

	@Test
	void getReturnById_Success() throws Exception {
		when(returnsService.getReturnById(1L)).thenReturn(testReturn);

		mockMvc.perform(get("/api/returns/1")).andExpect(status().isOk()).andExpect(jsonPath("$.returnId").value(1L));
		verify(returnsService, times(1)).getReturnById(1L);
	}

	@Test
	void deleteReturn_Success() throws Exception {
		doNothing().when(returnsService).deleteReturn(1L);

		mockMvc.perform(delete("/api/returns/1")).andExpect(status().isNoContent());
		verify(returnsService, times(1)).deleteReturn(1L);
	}
}