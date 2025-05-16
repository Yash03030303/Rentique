package com.capgemini.equipment_rental.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.services.EquipmentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/equipment")
@Slf4j
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<Equipment> createEquipment(@Valid @RequestBody Equipment equipment, BindingResult result) {
        log.info("Received request to create equipment: {}", equipment);

        if (result.hasErrors()) {
            log.warn("Validation failed for equipment creation: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        Equipment createdEquipment = equipmentService.createEquipment(equipment);
        log.info("Equipment created successfully with ID: {}", createdEquipment.getEquipmentId());

        return ResponseEntity
                .created(URI.create("/api/equipment/" + createdEquipment.getEquipmentId()))
                .body(createdEquipment);
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        log.info("Received request to fetch all equipment");
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        log.debug("Number of equipment items fetched: {}", equipmentList.size());
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        log.info("Fetching equipment with ID: {}", id);
        Equipment equipment = equipmentService.getEquipmentById(id);
        log.debug("Equipment fetched: {}", equipment);
        return ResponseEntity.ok(equipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody Equipment updatedEquipment,
            BindingResult result) {
        log.info("Received request to update equipment with ID: {}", id);

        if (result.hasErrors()) {
            log.warn("Validation failed while updating equipment: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }

        Equipment updated = equipmentService.updateEquipment(id, updatedEquipment);
        log.info("Equipment with ID {} updated successfully", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        log.info("Received request to delete equipment with ID: {}", id);
        equipmentService.deleteEquipment(id);
        log.info("Equipment with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
