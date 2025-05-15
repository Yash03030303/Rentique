package com.capgemini.equipment_rental.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.services.RentalItemsService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rental-items")
@Slf4j
public class RentalItemsController {

    private final RentalItemsService rentalItemsService;

    @Autowired
    public RentalItemsController(RentalItemsService rentalItemsService) {
        this.rentalItemsService = rentalItemsService;
    }

    // Create new rental item
    @PostMapping
    public ResponseEntity<RentalItems> createRentalItem(@Valid @RequestBody RentalItems rentalItem, BindingResult result) {
        log.info("Received request to create rental item: {}", rentalItem);

        if (result.hasErrors()) {
            log.warn("Validation failed for rental item creation: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        RentalItems createdItem = rentalItemsService.createRentalItem(rentalItem);
        log.info("Rental item created successfully with ID: {}", createdItem.getRentalItemId());

        return ResponseEntity
                .created(URI.create("/api/rental-items/" + createdItem.getRentalItemId()))
                .body(createdItem);
    }

    // Get all rental items
    @GetMapping
    public ResponseEntity<List<RentalItems>> getAllRentalItems() {
        log.info("Received request to fetch all rental items");
        List<RentalItems> items = rentalItemsService.getAllRentalItems();
        log.debug("Number of rental items fetched: {}", items.size());
        return ResponseEntity.ok(items);
    }

    // Get rental item by ID
    @GetMapping("/{id}")
    public ResponseEntity<RentalItems> getRentalItemById(@PathVariable Long id) {
        log.info("Fetching rental item with ID: {}", id);
        RentalItems item = rentalItemsService.getRentalItemById(id);
        log.debug("Rental item fetched: {}", item);
        return ResponseEntity.ok(item);
    }

    // Update rental item
    @PutMapping("/{id}")
    public ResponseEntity<RentalItems> updateRentalItem(@PathVariable Long id, @Valid @RequestBody RentalItems updatedItem, BindingResult result) {
        log.info("Received request to update rental item with ID: {}", id);

        if (result.hasErrors()) {
            log.warn("Validation failed while updating rental item: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        RentalItems updated = rentalItemsService.updateRentalItem(id, updatedItem);
        log.info("Rental item with ID {} updated successfully", id);
        return ResponseEntity.ok(updated);
    }

    // Delete rental item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalItem(@PathVariable Long id) {
        log.info("Received request to delete rental item with ID: {}", id);
        rentalItemsService.deleteRentalItem(id);
        log.info("Rental item with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
