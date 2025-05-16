package com.capgemini.equipment_rental.controllers;

import java.net.URI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.services.UsersService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersController {

	private final UsersService usersService;

	@Autowired
	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping
	public ResponseEntity<Users> createUser(@Valid @RequestBody Users user, BindingResult result) {
		log.info("Received request to create user: {}", user);
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Users createdUser = usersService.createUser(user);
		log.debug("User created with ID: {}", createdUser.getUserId());
		return ResponseEntity.created(URI.create("/api/users/" + createdUser.getUserId())).body(createdUser);
	}


	@GetMapping
	public ResponseEntity<List<Users>> getAllUsers() {
		log.info("Received request to fetch all users");
		List<Users> users = usersService.getAllUsers();
		log.debug("Returning {} users", users.size());
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable Long id) {
		log.info("Received request to fetch user with ID: {}", id);
		Users user = usersService.getUserById(id);
		log.debug("Employee fetched: {}", user);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users updatedUser,
			BindingResult result) {
		log.info("Received request to update user with ID: {}", id);
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Users user = usersService.updateUser(id, updatedUser);
		log.debug("User update with ID: {}", updatedUser.getUserId());
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("Received request to delete user with ID: {}", id); 
		usersService.deleteUser(id);
		log.info("User with ID {} successfully deleted", id);
		return ResponseEntity.noContent().build();
	}
}
