package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.exceptions.EquipmentNotFoundException;
import com.capgemini.equipment_rental.repositories.EquipmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment createEquipment(Equipment equipment) {
        log.info("Creating new equipment: {}", equipment.getName());
        Equipment savedEquipment = equipmentRepository.save(equipment);
        log.info("Equipment created with ID: {}", savedEquipment.getEquipmentId());
        return savedEquipment;
    }

    @Override
    public Equipment getEquipmentById(Long equipmentId) {
        log.info("Fetching equipment with ID: {}", equipmentId);
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> {
                    log.warn("Equipment not found with ID: {}", equipmentId);
                    return new EquipmentNotFoundException("Equipment with ID " + equipmentId + " not found.");
                });
    }

    @Override
    public List<Equipment> getAllEquipment() {
        log.info("Fetching all equipment from repository");
        List<Equipment> equipmentList = equipmentRepository.findAll();
        log.debug("Total equipment found: {}", equipmentList.size());
        return equipmentList;
    }

    @Override
    public Equipment updateEquipment(Long equipmentId, Equipment updatedEquipment) {
        log.info("Updating equipment with ID: {}", equipmentId);
        Equipment existingEquipment = getEquipmentById(equipmentId);

        log.debug("Updating fields for equipment ID: {}", equipmentId);
        existingEquipment.setName(updatedEquipment.getName());
        existingEquipment.setRentalPricePerDay(updatedEquipment.getRentalPricePerDay());
        existingEquipment.setStock(updatedEquipment.getStock());
        existingEquipment.setCategories(updatedEquipment.getCategories());

        Equipment savedEquipment = equipmentRepository.save(existingEquipment);
        log.info("Equipment with ID {} updated successfully", equipmentId);
        return savedEquipment;
    }

    @Override
    public void deleteEquipment(Long equipmentId) {
        log.info("Attempting to delete equipment with ID: {}", equipmentId);
        if (!equipmentRepository.existsById(equipmentId)) {
            log.warn("Equipment not found with ID: {}", equipmentId);
            throw new EquipmentNotFoundException("Equipment with ID " + equipmentId + " not found.");
        }
        equipmentRepository.deleteById(equipmentId);
        log.info("Equipment with ID {} deleted successfully", equipmentId);
    }
}
