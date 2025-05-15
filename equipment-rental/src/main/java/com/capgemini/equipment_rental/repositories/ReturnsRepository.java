package com.capgemini.equipment_rental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Returns;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Long> {

}
