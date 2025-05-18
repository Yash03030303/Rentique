package com.capgemini.equipment_rental.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.dto.LongestRental;
import com.capgemini.equipment_rental.dto.MonthlySpending;
import com.capgemini.equipment_rental.dto.MostRentedEquipment;
import com.capgemini.equipment_rental.dto.UserAnalytics;
import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.exceptions.RentalNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RentalsServiceImpl implements RentalsService {

	private final RentalsRepository rentalsRepository;

	@Autowired
	public RentalsServiceImpl(RentalsRepository rentalsRepository) {
		this.rentalsRepository = rentalsRepository;
	}

	@Override

	public Rentals createRentals(Rentals rental) {

		log.info("Creating new rental for user ID: {}", rental.getUser() != null ? rental.getUser().getUserId() : "null");
		Rentals savedRental = rentalsRepository.save(rental);
		log.info("Rental created with ID: {}", savedRental.getRentalId());
		return savedRental;
	}

	@Override

	public Rentals getRentalsById(Long rentalId) {

		log.info("Fetching rental with ID: {}", rentalId);
		return rentalsRepository.findById(rentalId).orElseThrow(() -> {
			log.warn("Rental not found with ID: {}", rentalId);
			return new RentalNotFoundException("Rental with ID " + rentalId + " not found.");
		});
	}

	@Override
	public List<Rentals> getAllRentals() {
		log.info("Fetching all rental records");
		List<Rentals> rentals = rentalsRepository.findAll();
		log.debug("Number of rentals found: {}", rentals.size());
		return rentals;
	}

	@Override

	public Rentals updateRentals(Long rentalId, Rentals updatedRental) {
		log.info("Attempting to update rental with ID: {}", rentalId);
		Rentals existingRental = getRentalsById(rentalId);

		log.debug("Updating rental fields for ID: {}", rentalId);
		existingRental.setRentalDate(updatedRental.getRentalDate());
		existingRental.setDueDate(updatedRental.getDueDate());
		existingRental.setTotalAmount(updatedRental.getTotalAmount());
		existingRental.setUser(updatedRental.getUser());
		existingRental.setRentalItems(updatedRental.getRentalItems());

		Rentals savedRental = rentalsRepository.save(existingRental);
		log.info("Rental with ID {} updated successfully", rentalId);
		return savedRental;
	}

	@Override
	public void deleteRentals(Long rentalId) {

		log.info("Attempting to delete rental with ID: {}", rentalId);

		if (!rentalsRepository.existsById(rentalId)) {
			log.warn("Rental not found with ID: {}", rentalId);
			throw new RentalNotFoundException("Rental with ID " + rentalId + " not found.");
		}

		rentalsRepository.deleteById(rentalId);
		log.info("Rental with ID {} deleted successfully", rentalId);
	}
	
	@Override
    public List<Rentals> getRentalsByUserId(Long userId) {
        log.info("Fetching rentals for user ID: {}", userId);
        return rentalsRepository.findByUserUserId(userId);
    }

    @Override
    public UserAnalytics getUserAnalytics(Long userId) {
        log.info("Generating analytics for user ID: {}", userId);
        List<Rentals> userRentals = getRentalsByUserId(userId);
        
        UserAnalytics analytics = new UserAnalytics();
        
        // Total amount spent
        BigDecimal totalSpent = userRentals.stream()
            .map(Rentals::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        analytics.setTotalAmountSpent(totalSpent);
        
        // Average rental cost
        BigDecimal avgCost = userRentals.isEmpty() ? BigDecimal.ZERO :
            totalSpent.divide(new BigDecimal(userRentals.size()), 2, BigDecimal.ROUND_HALF_UP);
        analytics.setAverageRentalCost(avgCost);
        
        // Monthly breakdown
        List<MonthlySpending> monthlyData = userRentals.stream()
            .collect(Collectors.groupingBy(
                rental -> rental.getRentalDate().getMonth().toString(),
                Collectors.reducing(BigDecimal.ZERO, Rentals::getTotalAmount, BigDecimal::add)
            ))
            .entrySet().stream()
            .map(entry -> new MonthlySpending(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        analytics.setMonthlyBreakdown(monthlyData);
        
        // Top rented equipment
        List<MostRentedEquipment> topEquipment = userRentals.stream()
            .flatMap(rental -> rental.getRentalItems().stream())
            .collect(Collectors.groupingBy(
                item -> item.getEquipment().getName(),
                Collectors.counting()
            ))
            .entrySet().stream()
            .map(entry -> new MostRentedEquipment(entry.getKey(), entry.getValue()))
            .sorted(Comparator.comparing(MostRentedEquipment::getRentalCount).reversed())
            .limit(3)
            .collect(Collectors.toList());
        analytics.setTopRentedEquipment(topEquipment);
        
        // Longest rental duration
        userRentals.stream()
            .max(Comparator.comparing(rental -> 
                ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getDueDate())))
            .ifPresent(longestRental -> {
                long days = ChronoUnit.DAYS.between(
                    longestRental.getRentalDate(), 
                    longestRental.getDueDate());
                RentalItems longestItem = longestRental.getRentalItems().stream()
                    .max(Comparator.comparing(RentalItems::getDaysRented))
                    .orElse(null);
                
                if (longestItem != null) {
                    analytics.setLongestRental(new LongestRental(
                        longestItem.getEquipment().getName(),
                        days
                    ));
                }
            });
        
        log.info("Analytics generated successfully for user ID: {}", userId);
        return analytics;
    }
    
    
    private UserAnalytics createEmptyAnalytics() {
        UserAnalytics analytics = new UserAnalytics();
        analytics.setTotalAmountSpent(BigDecimal.ZERO);
        analytics.setAverageRentalCost(BigDecimal.ZERO);
        // Set other empty/default values
        return analytics;
    }
	
}
