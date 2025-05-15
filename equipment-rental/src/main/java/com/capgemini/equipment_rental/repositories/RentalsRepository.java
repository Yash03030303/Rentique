package com.capgemini.equipment_rental.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Rentals;

@Repository
public interface RentalsRepository extends JpaRepository<Rentals, Long> {

}
