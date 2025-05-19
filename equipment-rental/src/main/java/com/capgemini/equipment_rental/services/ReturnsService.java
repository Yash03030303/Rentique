package com.capgemini.equipment_rental.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.capgemini.equipment_rental.entity.Returns;

public interface ReturnsService {

	Returns createReturn(Returns returns);

	Returns getReturnById(Long returnId);

	List<Returns> getAllReturns();

	void deleteReturn(Long returnId);

	Page<Returns> getReturnsByConditionAndDateRange(String itemCondition, LocalDate startDate, LocalDate endDate,
			Pageable pageable);
}
