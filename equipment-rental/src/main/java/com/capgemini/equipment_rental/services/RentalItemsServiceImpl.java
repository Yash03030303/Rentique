package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.exceptions.RentalItemNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalItemsRepository;

@Service
public class RentalItemsServiceImpl implements RentalItemsService {

	private RentalItemsRepository rentalItemsRepository;

	@Autowired
	public RentalItemsServiceImpl(RentalItemsRepository rentalItemsRepository) {
		super();
		this.rentalItemsRepository = rentalItemsRepository;
	}

	@Override
	public RentalItems createRentalItem(RentalItems rentalItem) {
		return rentalItemsRepository.save(rentalItem);
	}

	@Override
	public RentalItems getRentalItemById(Long rentalItemId) {
		return rentalItemsRepository.findById(rentalItemId).orElseThrow(
				() -> new RentalItemNotFoundException("Rental item with ID " + rentalItemId + " not found."));
	}

	@Override
	public List<RentalItems> getAllRentalItems() {
		return rentalItemsRepository.findAll();
	}

	@Override
	public RentalItems updateRentalItem(Long rentalItemId, RentalItems updatedRentalItem) {
		RentalItems existingRentalItem = getRentalItemById(rentalItemId);

		existingRentalItem.setQuantity(updatedRentalItem.getQuantity());
		existingRentalItem.setDaysRented(updatedRentalItem.getDaysRented());
		existingRentalItem.setRental(updatedRentalItem.getRental());
		existingRentalItem.setEquipment(updatedRentalItem.getEquipment());

		return rentalItemsRepository.save(existingRentalItem);
	}

	@Override
	public void deleteRentalItem(Long rentalItemId) {
		if (!rentalItemsRepository.existsById(rentalItemId)) {
			throw new RentalItemNotFoundException("Rental item with ID " + rentalItemId + " not found.");
		}
		rentalItemsRepository.deleteById(rentalItemId);
	}
}
