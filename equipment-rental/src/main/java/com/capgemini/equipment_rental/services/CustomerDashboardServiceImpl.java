package com.capgemini.equipment_rental.services;

import com.capgemini.equipment_rental.dto.CustomerDashboardDTO;

import com.capgemini.equipment_rental.repositories.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerDashboardServiceImpl implements CustomerDashboardService {

    private final RentalsRepository rentalsRepository;

    @Autowired
    public CustomerDashboardServiceImpl(RentalsRepository rentalsRepository) {
        this.rentalsRepository = rentalsRepository;
    }

    @Override
    public CustomerDashboardDTO getCustomerDashboard(Long userId) {
        CustomerDashboardDTO dto = new CustomerDashboardDTO();

        // 1. Active Rentals
        dto.setActiveRentals(rentalsRepository.countActiveRentals(userId));

        // 2. Overdue Returns
        dto.setOverdueReturns(rentalsRepository.countOverdueRentals(userId, LocalDate.now()));

        // 3. Total Rentals (using active rentals if no separate total method)
        dto.setTotalRentals(rentalsRepository.countActiveRentals(userId));

        // 4. Upcoming Returns (next 7 days)
        dto.setUpcomingReturns(
            rentalsRepository.countUpcomingReturns(userId, LocalDate.now(), LocalDate.now().plusDays(7))
        );

        // 5. Rentals by Category
        Map<String, Integer> rentalCategories = new HashMap<>();
        List<Object[]> categoryResults = rentalsRepository.countRentalsByCategory(userId);
        for (Object[] row : categoryResults) {
            String category = (String) row[0];
            Long count = (Long) row[1];
            rentalCategories.put(category, count.intValue());
        }
        dto.setRentalCategories(rentalCategories);

        // 6. Monthly Rentals (for current year)
        int currentYear = LocalDate.now().getYear();
        List<Object[]> monthlyResults = rentalsRepository.countMonthlyRentals(userId, currentYear);
        List<Integer> monthlyRentals = new ArrayList<>(Collections.nCopies(12, 0));
        for (Object[] row : monthlyResults) {
            Integer month = ((Number) row[0]).intValue(); // 1 = Jan, 12 = Dec
            Long count = (Long) row[1];
            monthlyRentals.set(month - 1, count.intValue());
        }
        dto.setMonthlyRentals(monthlyRentals);

        return dto;
    }
}

