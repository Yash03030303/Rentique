package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.entity.RentalItems;

public interface RentalItemService {
	
    RentalItems addRentalItem(RentalItems item);
    List<RentalItems> getAllRentalItems();
    RentalItems getRentalItemById(Long id);
    List<RentalItems> getRentalItemsByRentalId(Long rentalId);
    RentalItems updateRentalItem(Long id, RentalItems updatedItem);
    void deleteRentalItem(Long id);
}
