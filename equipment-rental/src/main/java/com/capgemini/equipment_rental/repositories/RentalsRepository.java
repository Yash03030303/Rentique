package com.capgemini.equipment_rental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.equipment_rental.entity.Rentals;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {
}

