package com.capgemini.equipment_rental.service;

import java.util.*;

import com.capgemini.equipment_rental.entity.Equipment;

public interface EquipmentService {
	List<Equipment> getAllEquipment();

	Equipment getEquipmentById(Long equipmentId);

	Equipment createEquipment(Equipment equipment);

	Equipment updateEquipment(Long equipmentId, Equipment equipment);

	Equipment patchEquipment(Long equipmentId, Equipment equipment);

	void deleteEquipment(Long equipmentId);
}
