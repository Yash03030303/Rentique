package com.capgemini.equipment_rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.service.EquipmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @PostMapping
    public ResponseEntity<Equipment> createEquipment(@Valid @RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.createEquipment(equipment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(
            @PathVariable("id") Long id,
            @Valid @RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.updateEquipment(id, equipment));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Equipment> patchEquipment(
            @PathVariable("id") Long id,
            @RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.patchEquipment(id, equipment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
