package com.capgemini.equipment_rental.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.dto.DashboardDTO;
import com.capgemini.equipment_rental.repositories.EquipmentRepository;
import com.capgemini.equipment_rental.repositories.RentalsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
	private final EquipmentRepository equipmentRepo;
	private final RentalsRepository rentalsRepo;

	public DashboardDTO getDashboardData() {
		DashboardDTO dto = new DashboardDTO(equipmentRepo.countTotalEquipment(), rentalsRepo.countActiveRentals());

		dto.setEquipmentDistribution(convertToMap(equipmentRepo.getEquipmentDistribution()));

		dto.setMonthlyRentals(convertToMap(rentalsRepo.getMonthlyRentals()));

		return dto;
	}

	private Map<String, Long> convertToMap(List<Object[]> data) {
		return data.stream().collect(Collectors.toMap(e -> (String) e[0], e -> (Long) e[1]));
	}
}