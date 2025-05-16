package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.exceptions.RentalItemNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalItemsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RentalItemsServiceImpl implements RentalItemsService {

	private final RentalItemsRepository rentalItemsRepository;

	@Autowired
	public RentalItemsServiceImpl(RentalItemsRepository rentalItemsRepository) {
		this.rentalItemsRepository = rentalItemsRepository;
	}

	@Override
	public RentalItems createRentalItem(RentalItems rentalItem) {
		log.info("Creating rental item for equipment ID: {}", rentalItem.getEquipment() != null ? rentalItem.getEquipment().getEquipmentId() : "null");
		RentalItems savedItem = rentalItemsRepository.save(rentalItem);
		log.info("Rental item created with ID: {}", savedItem.getRentalItemId());
		return savedItem;
	}

	@Override
	public RentalItems getRentalItemById(Long rentalItemId) {
		log.info("Fetching rental item with ID: {}", rentalItemId);
		return rentalItemsRepository.findById(rentalItemId).orElseThrow(() -> {
			log.warn("Rental item not found with ID: {}", rentalItemId);
			return new RentalItemNotFoundException("Rental item with ID " + rentalItemId + " not found.");
		});
	}

	@Override
	public List<RentalItems> getAllRentalItems() {
		log.info("Fetching all rental items from repository");
		List<RentalItems> rentalItems = rentalItemsRepository.findAll();
		log.debug("Total rental items found: {}", rentalItems.size());
		return rentalItems;
	}

	@Override
	public RentalItems updateRentalItem(Long rentalItemId, RentalItems updatedRentalItem) {
		log.info("Attempting to update rental item with ID: {}", rentalItemId);
		RentalItems existingRentalItem = getRentalItemById(rentalItemId);

		log.debug("Updating fields for rental item ID: {}", rentalItemId);
		existingRentalItem.setQuantity(updatedRentalItem.getQuantity());
		existingRentalItem.setDaysRented(updatedRentalItem.getDaysRented());
		existingRentalItem.setRental(updatedRentalItem.getRental());
		existingRentalItem.setEquipment(updatedRentalItem.getEquipment());

		RentalItems savedItem = rentalItemsRepository.save(existingRentalItem);
		log.info("Rental item with ID {} updated successfully", rentalItemId);
		return savedItem;
	}

	@Override
	public void deleteRentalItem(Long rentalItemId) {
		log.info("Attempting to delete rental item with ID: {}", rentalItemId);

		if (!rentalItemsRepository.existsById(rentalItemId)) {
			log.warn("Rental item not found with ID: {}", rentalItemId);
			throw new RentalItemNotFoundException("Rental item with ID " + rentalItemId + " not found.");
		}

		rentalItemsRepository.deleteById(rentalItemId);
		log.info("Rental item with ID {} deleted successfully", rentalItemId);
	}
}
