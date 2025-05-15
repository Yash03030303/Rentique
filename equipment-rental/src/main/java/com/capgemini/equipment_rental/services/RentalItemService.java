package com.capgemini.equipment_rental.services;
//
//import com.capgemini.equipment_rental.entity.RentalItems;
//
//import java.time.LocalDate;
//import java.util.*;
//
//public interface RentalItemService {
//	
//	RentalItems saveRentalItem(RentalItems item);
//    List<RentalItems> getRentalItemsByRentalId(Long rentalId);
//    List<RentalItems> getCurrentlyRentedItems();
//    List<RentalItems> getOverdueItems();
//    List<Object[]> getRentalCountByEquipment();
//    List<RentalItems> getRentalItemsByDateRange(LocalDate start, LocalDate end);
//
//}


//package com.capgemini.equipment_rental.service;

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
