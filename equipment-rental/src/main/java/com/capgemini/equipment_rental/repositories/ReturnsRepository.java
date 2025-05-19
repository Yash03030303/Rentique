package com.capgemini.equipment_rental.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Returns;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Long> {
	Page<Returns> findByItemConditionContainingIgnoreCaseAndReturnDateBetween(
		    String itemCondition, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
