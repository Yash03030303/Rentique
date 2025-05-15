package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.exceptions.InvalidDataException;
import com.capgemini.equipment_rental.exceptions.RentalNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalsRepository;

import jakarta.validation.Valid;

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
	public Rentals createRentals(@Valid Rentals rentals) {
		if (rentals.getRentalDate().isAfter(rentals.getDueDate())) {
			throw new InvalidDataException("RentalDate should always less than DueDate.");
		}
		return rentalsRepository.save(rentals);
	}

	@Override
	public Rentals updateRentals(Long rentalId, @Valid Rentals rentals) {
		if (rentals.getRentalDate().isAfter(rentals.getDueDate())) {
			throw new InvalidDataException("RentalDate should always less than DueDate.");
		}
		Rentals existing = rentalsRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rentals not found with ID: " + rentalId));
		existing.setRentalDate(rentals.getRentalDate());
		existing.setDueDate(rentals.getDueDate());
		existing.setTotalAmount(rentals.getTotalAmount());
		existing.setUser(rentals.getUser());
		existing.setReturns(rentals.getReturns());
		existing.setRentalItems(rentals.getRentalItems());
		return rentalsRepository.save(existing);

	}

	@Override
	public Rentals patchRentals(Long rentalId, Rentals rentals) {
		if (rentals.getRentalDate().isAfter(rentals.getDueDate())) {
			throw new InvalidDataException("RentalDate should always less than DueDate.");
		}
		Rentals existing = rentalsRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rentals not found with ID: " + rentalId));

		if (rentals.getRentalDate() != null) {
			existing.setRentalDate(rentals.getRentalDate());
		}
		if (rentals.getDueDate() != null) {
			existing.setDueDate(rentals.getDueDate());
		}
		if (rentals.getTotalAmount() != null) {
			existing.setTotalAmount(rentals.getTotalAmount());
		}
		if (rentals.getUser() != null) {
			existing.setUser(rentals.getUser());
		}
		if (rentals.getReturns() != null) {
			existing.setReturns(rentals.getReturns());
		}
		if (rentals.getRentalItems() != null) {
			existing.setRentalItems(rentals.getRentalItems());
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
