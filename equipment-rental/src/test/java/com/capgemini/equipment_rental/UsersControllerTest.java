package com.capgemini.equipment_rental;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.equipment_rental.controllers.UsersController;
import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.entity.Users.UserType;
import com.capgemini.equipment_rental.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Mock
	private UsersService usersService;

	@InjectMocks
	private UsersController usersController;

	private Users user;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
		user = new Users(1L, "John Doe", "john@example.com", "Password@123", "9123456789", UserType.USER);
	}

	@Test
	void createUser_ShouldReturnCreated() throws Exception {
		when(usersService.createUser(any())).thenReturn(user);

		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.userId").value(1L));
	}

	@Test
	void getAllUsers_ShouldReturnUsersList() throws Exception {
		when(usersService.getAllUsers()).thenReturn(Collections.singletonList(user));

		mockMvc.perform(get("/api/users")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("John Doe"));
	}

	@Test
	void getUserById_ShouldReturnUser() throws Exception {
		when(usersService.getUserById(1L)).thenReturn(user);

		mockMvc.perform(get("/api/users/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("john@example.com"));
	}

	@Test
	void updateUser_ShouldReturnUpdatedUser() throws Exception {
		Users updatedUser = new Users(1L, "Jane Doe", "jane@example.com", "NewPass@123", "9876543210",
				UserType.SUPPLIER);
		when(usersService.updateUser(any(), any())).thenReturn(updatedUser);

		mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedUser))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Jane Doe"));
	}

	@Test
	void deleteUser_ShouldReturnNoContent() throws Exception {
		mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());
	}
}
