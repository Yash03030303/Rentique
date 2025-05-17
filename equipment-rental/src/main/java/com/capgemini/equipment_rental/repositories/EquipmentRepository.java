package com.capgemini.equipment_rental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

	boolean existsByName(String name);
	List<Equipment> findByCategories_CategoryId(Long categoryId);

	
//	    List<Equipment> findByStatus(String status);
}
