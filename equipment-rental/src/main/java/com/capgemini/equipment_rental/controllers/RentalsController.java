package com.capgemini.equipment_rental.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.services.RentalsService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/rentals")
public class RentalsController {
	private final RentalsService rentalsService;

	@Autowired
	public RentalsController(RentalsService rentalsService) {
		this.rentalsService = rentalsService;
	}

	@GetMapping
	public ResponseEntity<List<Rentals>> getAllRentals() {
		List<Rentals> rentals = rentalsService.getAllRentals();
		return ResponseEntity.status(HttpStatus.OK).body(rentals);
	}

	@GetMapping("/{rentalId}")
	public ResponseEntity<Rentals> getRentals(@PathVariable Long rentalId) {
		Rentals rental = rentalsService.getRentalsById(rentalId);
		return ResponseEntity.status(HttpStatus.OK).body(rental);
	}

	@PostMapping
	public ResponseEntity<Rentals> createRentals(@Valid @RequestBody Rentals rentals) {
		Rentals saved = rentalsService.createRentals(rentals);
		return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/api/rentals/" + saved.getRentalId()))
				.body(saved);
	}

	@PutMapping("/{rentalId}")
	public ResponseEntity<Rentals> updateRentals(@PathVariable Long rentalId, @Valid  @RequestBody Rentals newRental) {
		Rentals updated = rentalsService.updateRentals(rentalId, newRental);
		return ResponseEntity.status(HttpStatus.OK).body(updated);
	}

	@DeleteMapping("/{rentalId}")
	public ResponseEntity<Void> deleteRentals(@PathVariable Long rentalId) {
		rentalsService.deleteRentals(rentalId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
