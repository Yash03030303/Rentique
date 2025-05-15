package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.exceptions.EquipmentNotFoundException;
import com.capgemini.equipment_rental.repositories.EquipmentRepository;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	private EquipmentRepository equipmentRepository;

	@Autowired
	public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
		super();
		this.equipmentRepository = equipmentRepository;
	}

	@Override
	public Equipment createEquipment(Equipment equipment) {
		return equipmentRepository.save(equipment);
	}

	@Override
	public Equipment getEquipmentById(Long equipmentId) {
		return equipmentRepository.findById(equipmentId).orElseThrow(() -> new EquipmentNotFoundException("Equipment with ID " + equipmentId + " not found."));
	}

	@Override
	public List<Equipment> getAllEquipment() {
		return equipmentRepository.findAll();
	}

	@Override
	public Equipment updateEquipment(Long equipmentId, Equipment updatedEquipment) {
		Equipment existingEquipment = getEquipmentById(equipmentId);

		existingEquipment.setName(updatedEquipment.getName());
		existingEquipment.setRentalPricePerDay(updatedEquipment.getRentalPricePerDay());
		existingEquipment.setStock(updatedEquipment.getStock());
		existingEquipment.setCategories(updatedEquipment.getCategories());

		return equipmentRepository.save(existingEquipment);
	}

	@Override
	public void deleteEquipment(Long equipmentId) {
		if (!equipmentRepository.existsById(equipmentId)) {
			throw new EquipmentNotFoundException("Equipment with ID " + equipmentId + " not found.");
		}
		equipmentRepository.deleteById(equipmentId);
	}
}
