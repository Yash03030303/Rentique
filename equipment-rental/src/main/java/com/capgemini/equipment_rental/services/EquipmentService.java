package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.entity.Equipment;

public interface EquipmentService {
    Equipment createEquipment(Equipment equipment);

    Equipment getEquipmentById(Long equipmentId);

    List<Equipment> getAllEquipment();

    Equipment updateEquipment(Long equipmentId, Equipment equipment);

    void deleteEquipment(Long equipmentId);
}
