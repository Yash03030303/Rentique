package com.capgemini.equipment_rental.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.services.RentalsService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rentals")
@Slf4j
public class RentalsController {

    private final RentalsService rentalsService;

    @Autowired
    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    // Create a new rental
    @PostMapping
    public ResponseEntity<Rentals> createRental(@Valid @RequestBody Rentals rental, BindingResult result) {
        log.info("Received request to create rental: {}", rental);

        if (result.hasErrors()) {
            log.warn("Validation failed while creating rental: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        Rentals createdRental = rentalsService.createRentals(rental);
        log.info("Rental created successfully with ID: {}", createdRental.getRentalId());

        return ResponseEntity
                .created(URI.create("/api/rentals/" + createdRental.getRentalId()))
                .body(createdRental);
    }

    @GetMapping
    public ResponseEntity<List<Rentals>> getAllRentals() {
        log.info("Received request to fetch all rentals");
        List<Rentals> rentals = rentalsService.getAllRentals();
        log.debug("Number of rentals fetched: {}", rentals.size());
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rentals> getRentalById(@PathVariable Long id) {
        log.info("Fetching rental with ID: {}", id);
        Rentals rental = rentalsService.getRentalsById(id);
        log.debug("Rental fetched: {}", rental);
        return ResponseEntity.ok(rental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rentals> updateRental(@PathVariable Long id,
                                                @Valid @RequestBody Rentals updatedRental,
                                                BindingResult result) {
        log.info("Received request to update rental with ID: {}", id);

        if (result.hasErrors()) {
            log.warn("Validation failed while updating rental: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        Rentals updated = rentalsService.updateRentals(id, updatedRental);
        log.info("Rental with ID {} updated successfully", id);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        log.info("Received request to delete rental with ID: {}", id);
        rentalsService.deleteRentals(id);
        log.info("Rental with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
