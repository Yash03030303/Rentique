package com.capgemini.equipment_rental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.equipment_rental.entity.Rentals;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {

	List<Rentals> findByUserUserId(Long userId); // Add this method
}

