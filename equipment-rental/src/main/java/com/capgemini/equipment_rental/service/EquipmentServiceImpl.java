package com.capgemini.equipment_rental.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.repositories.EquipmentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepo equipmentRepository;

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment getEquipmentById(Long equipmentId) {
        return equipmentRepository.findById(equipmentId).orElseThrow(() -> new EntityNotFoundException("Equipment not found with ID: " + equipmentId));
    }

    @Override
    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Long equipmentId, Equipment updatedEquipment) {
        Equipment existingEquipment = getEquipmentById(equipmentId);
        existingEquipment.setName(updatedEquipment.getName());
        existingEquipment.setCategory(updatedEquipment.getCategory());
        existingEquipment.setRentalPricePerDay(updatedEquipment.getRentalPricePerDay());
        existingEquipment.setStock(updatedEquipment.getStock());
        return equipmentRepository.save(existingEquipment);
    }

    @Override
    public Equipment patchEquipment(Long equipmentId, Equipment patchData) {
        Equipment existingEquipment = getEquipmentById(equipmentId);

        if (patchData.getName() != null) {
            existingEquipment.setName(patchData.getName());
        }
        if (patchData.getCategory() != null) {
            existingEquipment.setCategory(patchData.getCategory());
        }
        if (patchData.getRentalPricePerDay() != null) {
            existingEquipment.setRentalPricePerDay(patchData.getRentalPricePerDay());
        }
        if (patchData.getStock() != null) {
            existingEquipment.setStock(patchData.getStock());
        }

        return equipmentRepository.save(existingEquipment);
    }

    @Override
    public void deleteEquipment(Long equipmentId) {
        Equipment equipment = getEquipmentById(equipmentId);
        equipmentRepository.delete(equipment);
    }
}
