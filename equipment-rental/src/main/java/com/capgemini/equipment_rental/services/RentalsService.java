package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.dto.UserAnalytics;
import com.capgemini.equipment_rental.entity.Rentals;

public interface RentalsService {
	List<Rentals> getAllRentals();

	Rentals getRentalsById(Long rentalId);

	Rentals createRentals(Rentals rentals);

	Rentals updateRentals(Long rentalId, Rentals rentals);

	void deleteRentals(Long rentalId);
	
	UserAnalytics getUserAnalytics(Long userId);
	List<Rentals> getRentalsByUserId(Long userId);
}
