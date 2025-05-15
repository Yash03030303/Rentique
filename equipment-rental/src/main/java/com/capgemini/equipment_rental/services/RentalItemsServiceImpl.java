package com.capgemini.equipment_rental.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.repositories.RentalItemsRepository;


@Service
public class RentalItemsServiceImpl implements RentalItemService {

    @Autowired
    private RentalItemsRepository rentalItemsRepository;
    

    @Override
    public RentalItems addRentalItem(RentalItems item) {
        return rentalItemsRepository.save(item);
    }

    @Override
    public List<RentalItems> getAllRentalItems() {
        return rentalItemsRepository.findAll();
    }

    @Override
    public RentalItems getRentalItemById(Long id) {
        return rentalItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental item not found with ID: " + id));
    }

    @Override
    public List<RentalItems> getRentalItemsByRentalId(Long rentalId) {
        return rentalItemsRepository.findByRentalId(rentalId);
    }

    @Override
    public RentalItems updateRentalItem(Long id, RentalItems updatedItem) {
        RentalItems existing = getRentalItemById(id);
        existing.setEquipmentId(updatedItem.getEquipmentId());
        existing.setQuantity(updatedItem.getQuantity());
        existing.setDaysRented(updatedItem.getDaysRented());
        return rentalItemsRepository.save(existing);
    }

    @Override
    public void deleteRentalItem(Long id) {
        rentalItemsRepository.deleteById(id);
    }
}

