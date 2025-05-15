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

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.services.RentalItemsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rental-items")
public class RentalItemsController {

    private final RentalItemsService rentalItemsService;

    @Autowired
    public RentalItemsController(RentalItemsService rentalItemsService) {
        this.rentalItemsService = rentalItemsService;
    }

    // Create new rental item
    @PostMapping
    public ResponseEntity<RentalItems> createRentalItem(@Valid @RequestBody RentalItems rentalItem,BindingResult result) {
    	if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid rentalitems data: " + result.getAllErrors());
		}
        RentalItems createdItem = rentalItemsService.createRentalItem(rentalItem);
        return ResponseEntity
                .created(URI.create("/api/rental-items/" + createdItem.getRentalItemId()))
                .body(createdItem);
    }

    // Get all rental items
    @GetMapping
    public ResponseEntity<List<RentalItems>> getAllRentalItems() {
        List<RentalItems> items = rentalItemsService.getAllRentalItems();
        return ResponseEntity.ok(items);
    }

    // Get rental item by ID
    @GetMapping("/{id}")
    public ResponseEntity<RentalItems> getRentalItemById(@PathVariable Long id) {
        RentalItems item = rentalItemsService.getRentalItemById(id);
        return ResponseEntity.ok(item);
    }

    // Update rental item
    @PutMapping("/{id}")
    public ResponseEntity<RentalItems> updateRentalItem(@PathVariable Long id, @Valid @RequestBody RentalItems updatedItem,BindingResult result) {
    	if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid rentalitems data: " + result.getAllErrors());
		}
        RentalItems updated = rentalItemsService.updateRentalItem(id, updatedItem);
        return ResponseEntity.ok(updated);
    }

    // Delete rental item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalItem(@PathVariable Long id) {
        rentalItemsService.deleteRentalItem(id);
        return ResponseEntity.noContent().build();
    }
}
