package com.capgemini.equipment_rental.services;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.capgemini.equipment_rental.entity.RentalItems;
//import com.capgemini.equipment_rental.repositories.RentalItemsRepository;
//@Service
//public class RentalItemsServiceImpl implements RentalItemService{
//	
//	@Autowired
//    private RentalItemsRepository rentalItemsRepository;
//	
//	@Override
//	public RentalItems saveRentalItem(RentalItems item) {
//		return rentalItemsRepository.save(item);
//	}
//
//	
//	@Override
//	public List<RentalItems> getRentalItemsByRentalId(Long rentalId) {
//		// TODO Auto-generated method stub
//		return rentalItemsRepository.findByRental_RentalId(rentalId);
//	}
//
//	@Override
//	public List<RentalItems> getCurrentlyRentedItems() {
//		// TODO Auto-generated method stub
//		return rentalItemsRepository.findCurrentlyRentedItems();
//	}
//
//	@Override
//	public List<RentalItems> getOverdueItems() {
//		// TODO Auto-generated method stub
//		return rentalItemsRepository.findOverdueItems(LocalDate.now());
//	}
//
//	@Override
//	public List<Object[]> getRentalCountByEquipment() {
//		return rentalItemsRepository.countRentalsByEquipment();
//	}
//
//	@Override
//	public List<RentalItems> getRentalItemsByDateRange(LocalDate start, LocalDate end) {
//		 return rentalItemsRepository.findRentalItemsByRentalDateBetween(start, end);
//	}
//
//	
//	
//	
//	
//
//}


//package com.capgemini.equipment_rental.service.impl;

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

