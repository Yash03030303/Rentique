package com.capgemini.equipment_rental.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.capgemini.equipment_rental.entity.Rentals;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {

	@Query("SELECT COUNT(r) FROM Rentals r WHERE r.user.userId = :userId")
    int countActiveRentals(@Param("userId") Long userId);
	
	@Query("SELECT e.categories.name, COUNT(r) " +
		       "FROM Rentals r JOIN r.rentalItems ri JOIN ri.equipment e " +
		       "WHERE r.user.userId = :userId " +
		       "GROUP BY e.categories.name")
		List<Object[]> countRentalsByCategory(@Param("userId") Long userId);

	    
		@Query("SELECT FUNCTION('MONTH', r.rentalDate), COUNT(r) " +
			       "FROM Rentals r " +
			       "WHERE r.user.userId = :userId AND FUNCTION('YEAR', r.rentalDate) = :year " +
			       "GROUP BY FUNCTION('MONTH', r.rentalDate) " +
			       "ORDER BY FUNCTION('MONTH', r.rentalDate)")
			List<Object[]> countMonthlyRentals(@Param("userId") Long userId, @Param("year") int year);

	     
	     @Query("SELECT COUNT(r) FROM Rentals r WHERE r.user.userId = :userId AND r.dueDate < :today")
	     int countOverdueRentals(@Param("userId") Long userId, @Param("today") LocalDate today);

	     @Query("SELECT COUNT(r) FROM Rentals r WHERE r.user.userId = :userId AND r.dueDate BETWEEN :today AND :upcoming")
	     int countUpcomingReturns(@Param("userId") Long userId, @Param("today") LocalDate today, @Param("upcoming") LocalDate upcoming);
	     
	     @Query("SELECT COALESCE(SUM(r.totalAmount), 0) FROM Rentals r WHERE r.user.userId = :userId")
	     BigDecimal totalAmountSpent(@Param("userId") Long userId);
	     
	     List<Rentals> findByUser_UserId(Long userId);


	@Query("SELECT COUNT(r) FROM Rentals r " + "WHERE NOT EXISTS (SELECT ret FROM Returns ret WHERE ret.rental = r)")
	Long countActiveRentals();

	List<Rentals> findByUserUserId(Long userId); // Add this method


	@Query("SELECT FUNCTION('DATE_FORMAT', r.rentalDate, '%Y-%m'), COUNT(r) "
			+ "FROM Rentals r GROUP BY FUNCTION('DATE_FORMAT', r.rentalDate, '%Y-%m')")
	List<Object[]> getMonthlyRentals();
}

