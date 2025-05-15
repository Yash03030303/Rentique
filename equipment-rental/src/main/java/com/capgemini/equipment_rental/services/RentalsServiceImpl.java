package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.exceptions.RentalNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalsRepository;

@Service
public class RentalsServiceImpl implements RentalsService {

	private RentalsRepository rentalsRepository;

	@Autowired
	public RentalsServiceImpl(RentalsRepository rentalsRepository) {
		this.rentalsRepository = rentalsRepository;
	}

	@Override
	public Rentals createRental(Rentals rental) {
		return rentalsRepository.save(rental);
	}

	@Override
	public Rentals getRentalById(Long rentalId) {
		return rentalsRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rental with ID " + rentalId + " not found."));
	}

	@Override
	public List<Rentals> getAllRentals() {
		return rentalsRepository.findAll();
	}

	@Override
	public Rentals updateRental(Long rentalId, Rentals updatedRental) {
		Rentals existingRental = getRentalById(rentalId);

		existingRental.setRentalDate(updatedRental.getRentalDate());
		existingRental.setDueDate(updatedRental.getDueDate());
		existingRental.setTotalAmount(updatedRental.getTotalAmount());
		existingRental.setUser(updatedRental.getUser());
		existingRental.setRentalItems(updatedRental.getRentalItems());

		return rentalsRepository.save(existingRental);
	}

	@Override
	public void deleteRental(Long rentalId) {
		if (!rentalsRepository.existsById(rentalId)) {
			throw new RentalNotFoundException("Rental with ID " + rentalId + " not found.");
		}
		rentalsRepository.deleteById(rentalId);
	}
}
