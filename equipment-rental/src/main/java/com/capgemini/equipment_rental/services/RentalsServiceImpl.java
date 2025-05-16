package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.exceptions.RentalNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RentalsServiceImpl implements RentalsService {

	private final RentalsRepository rentalsRepository;

	@Autowired
	public RentalsServiceImpl(RentalsRepository rentalsRepository) {
		this.rentalsRepository = rentalsRepository;
	}

	@Override
	public Rentals createRentals(Rentals rental) {
		log.info("Creating new rental for user ID: {}", rental.getUser() != null ? rental.getUser().getUserId() : "null");
		Rentals savedRental = rentalsRepository.save(rental);
		log.info("Rental created with ID: {}", savedRental.getRentalId());
		return savedRental;
	}

	@Override
	public Rentals getRentalsById(Long rentalId) {
		log.info("Fetching rental with ID: {}", rentalId);
		return rentalsRepository.findById(rentalId).orElseThrow(() -> {
			log.warn("Rental not found with ID: {}", rentalId);
			return new RentalNotFoundException("Rental with ID " + rentalId + " not found.");
		});
	}

	@Override
	public List<Rentals> getAllRentals() {
		log.info("Fetching all rental records");
		List<Rentals> rentals = rentalsRepository.findAll();
		log.debug("Number of rentals found: {}", rentals.size());
		return rentals;
	}

	@Override
	public Rentals updateRentals(Long rentalId, Rentals updatedRental) {
		log.info("Attempting to update rental with ID: {}", rentalId);
		Rentals existingRental = getRentalsById(rentalId);

		log.debug("Updating rental fields for ID: {}", rentalId);
		existingRental.setRentalDate(updatedRental.getRentalDate());
		existingRental.setDueDate(updatedRental.getDueDate());
		existingRental.setTotalAmount(updatedRental.getTotalAmount());
		existingRental.setUser(updatedRental.getUser());
		existingRental.setRentalItems(updatedRental.getRentalItems());

		Rentals savedRental = rentalsRepository.save(existingRental);
		log.info("Rental with ID {} updated successfully", rentalId);
		return savedRental;
	}

	@Override
	public void deleteRentals(Long rentalId) {
		log.info("Attempting to delete rental with ID: {}", rentalId);

		if (!rentalsRepository.existsById(rentalId)) {
			log.warn("Rental not found with ID: {}", rentalId);
			throw new RentalNotFoundException("Rental with ID " + rentalId + " not found.");
		}

		rentalsRepository.deleteById(rentalId);
		log.info("Rental with ID {} deleted successfully", rentalId);
	}
}
