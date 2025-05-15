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

@RestController
@RequestMapping("/api/users")
public class UsersController {

	private final UsersService usersService;

	@Autowired
	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping
	public ResponseEntity<Users> createUser(@Valid @RequestBody Users user, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid user data: " + result.getAllErrors());
		}
		Users createdUser = usersService.createUser(user);
		return ResponseEntity.created(URI.create("/api/users/" + createdUser.getUserId())).body(createdUser);
	}


	@GetMapping
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> users = usersService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable Long id) {
		Users user = usersService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users updatedUser,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid user data: " + result.getAllErrors());
		}
		Users user = usersService.updateUser(id, updatedUser);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		usersService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
