package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.entity.RentalItems;

public interface RentalItemsService {

    RentalItems createRentalItem(RentalItems rentalItem);

    RentalItems getRentalItemById(Long rentalItemId);

    List<RentalItems> getAllRentalItems();

    RentalItems updateRentalItem(Long rentalItemId, RentalItems rentalItem);

    void deleteRentalItem(Long rentalItemId);
}
