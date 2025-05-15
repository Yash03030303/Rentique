package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.exceptions.InvalidDataException;
import com.capgemini.equipment_rental.exceptions.RentalNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalsRepository;

@Service
public class RentalsServiceImpl implements RentalsService {
	private final RentalsRepository rentalsRepository;

	@Autowired
	public RentalsServiceImpl(RentalsRepository rentalsRepository) {
		this.rentalsRepository = rentalsRepository;
	}

	@Override
	public List<Rentals> getAllRentals() {
		return rentalsRepository.findAll();
	}

	@Override
	public Rentals getRentalsById(Long rentalId) {
		return rentalsRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rentals not found with ID: " + rentalId));
	}

	@Override
	public Rentals createRentals(Rentals rentals) {
		if (rentals.getRentalDate().isAfter(rentals.getDueDate())) {
			throw new InvalidDataException("RentalDate should always less than DueDate.");
		}
		return rentalsRepository.save(rentals);
	}

	@Override
	public Rentals updateRentals(Long rentalId, Rentals rentals) {
		if (rentals.getRentalDate().isAfter(rentals.getDueDate())) {
			throw new InvalidDataException("RentalDate must be before DueDate.");
		}

		Rentals existing = rentalsRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rental not found with ID: " + rentalId));

		existing.setRentalDate(rentals.getRentalDate());
		existing.setDueDate(rentals.getDueDate());
		existing.setTotalAmount(rentals.getTotalAmount());
		existing.setUser(rentals.getUser());
		existing.setReturns(rentals.getReturns());

		if (rentals.getRentalItems() != null) {
			existing.getRentalItems().clear();
			existing.getRentalItems().addAll(rentals.getRentalItems());
			for (RentalItems item : existing.getRentalItems()) {
				item.setRental(existing);
			}
		}
		return rentalsRepository.save(existing);
	}

	@Override
	public void deleteRentals(Long rentalId) {
		if (!rentalsRepository.existsById(rentalId)) {
			throw new RentalNotFoundException("Cannot delete. Rentals not found with ID: " + rentalId);
		}
		rentalsRepository.deleteById(rentalId);
	}
}
