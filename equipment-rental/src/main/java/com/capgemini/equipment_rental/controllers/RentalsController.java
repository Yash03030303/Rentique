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

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.services.RentalsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    private final RentalsService rentalsService;

    @Autowired
    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    // Create a new rental
    @PostMapping
    public ResponseEntity<Rentals> createRental(@Valid @RequestBody Rentals rental,BindingResult result) {
    	if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid rental data: " + result.getAllErrors());
		}
        Rentals createdRental = rentalsService.createRental(rental);
        return ResponseEntity
                .created(URI.create("/api/rentals/" + createdRental.getRentalId()))
                .body(createdRental);
    }

    // Get all rentals
    @GetMapping
    public ResponseEntity<List<Rentals>> getAllRentals() {
        List<Rentals> rentals = rentalsService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    // Get a rental by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rentals> getRentalById(@PathVariable Long id) {
        Rentals rental = rentalsService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    // Update rental
    @PutMapping("/{id}")
    public ResponseEntity<Rentals> updateRental(@PathVariable Long id, @Valid @RequestBody Rentals updatedRental,BindingResult result) {
    	if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid rentals data: " + result.getAllErrors());
		}
        Rentals updated = rentalsService.updateRental(id, updatedRental);
        return ResponseEntity.ok(updated);
    }

    // Delete rental
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalsService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
