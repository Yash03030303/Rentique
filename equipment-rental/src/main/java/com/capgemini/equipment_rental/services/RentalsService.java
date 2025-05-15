package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.entity.Rentals;

public interface RentalsService {

	Rentals createRental(Rentals rental);

	Rentals getRentalById(Long rentalId);

	List<Rentals> getAllRentals();

	Rentals updateRental(Long rentalId, Rentals rental);

	void deleteRental(Long rentalId);

}
