package com.capgemini.equipment_rental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.equipment_rental.entity.Equipment;

public interface EquipmentRepo extends JpaRepository<Equipment, Long> {
}