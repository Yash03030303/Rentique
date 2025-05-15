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

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    // Create new equipment
    @PostMapping
    public ResponseEntity<Equipment> createEquipment(@Valid @RequestBody Equipment equipment, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Invalid equipment data: " + result.getAllErrors());
        }
        Equipment createdEquipment = equipmentService.createEquipment(equipment);
        return ResponseEntity
                .created(URI.create("/api/equipment/" + createdEquipment.getEquipmentId()))
                .body(createdEquipment);
    }

    // Get all equipment
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipmentList);
    }

    // Get equipment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        return ResponseEntity.ok(equipment);
    }

    // Update equipment
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody Equipment updatedEquipment,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Invalid equipment data: " + result.getAllErrors());
        }
        Equipment updated = equipmentService.updateEquipment(id, updatedEquipment);
        return ResponseEntity.ok(updated);
    }

    // Delete equipment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
