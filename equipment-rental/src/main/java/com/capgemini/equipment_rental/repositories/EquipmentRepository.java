package com.capgemini.equipment_rental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.*;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

	boolean existsByName(String name);
	List<Equipment> findByCategories_CategoryId(Long categoryId);

	@Query("SELECT COUNT(e) FROM Equipment e")
	Long countTotalEquipment();

	@Query("SELECT c.name, COUNT(e) FROM Equipment e " + "JOIN e.categories c GROUP BY c.name")
	List<Object[]> getEquipmentDistribution();

}
