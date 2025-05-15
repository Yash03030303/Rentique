package com.capgemini.equipment_rental.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.capgemini.equipment_rental.entity.RentalItems;
//
//import java.time.LocalDate;
//import java.util.*;
//
//public interface RentalItemsRepository extends JpaRepository<RentalItems, Long>{
//	List<RentalItems> findByRental_RentalId(Long rentalId);
//
//    @Query("SELECT ri.equipmentId, COUNT(ri) FROM RentalItems ri GROUP BY ri.equipmentId")
//    List<Object[]> countRentalsByEquipment();
//    
//    
//    
//    @Query("SELECT ri FROM RentalItems ri WHERE ri.rental.returned = false")
//    List<RentalItems> findCurrentlyRentedItems();
//
//    @Query("SELECT ri FROM RentalItems ri WHERE ri.rental.dueDate < :currentDate AND ri.rental.returned = false")
//    List<RentalItems> findOverdueItems(LocalDate currentDate);
//
//    List<RentalItems> findRentalItemsByRentalDateBetween(LocalDate start, LocalDate end);
//
//
//}



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.RentalItems;

@Repository
public interface RentalItemsRepository extends JpaRepository<RentalItems, Long> {
    List<RentalItems> findByRentalId(Long rentalId);
    List<RentalItems> findByEquipmentId(Long equipmentId);
}

