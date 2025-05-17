package com.capgemini.equipment_rental.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.equipment_rental.entity.Rentals;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {
	@Query("SELECT COUNT(r) FROM Rentals r " + "WHERE NOT EXISTS (SELECT ret FROM Returns ret WHERE ret.rental = r)")
	Long countActiveRentals();

	@Query("SELECT FUNCTION('DATE_FORMAT', r.rentalDate, '%Y-%m'), COUNT(r) "
			+ "FROM Rentals r GROUP BY FUNCTION('DATE_FORMAT', r.rentalDate, '%Y-%m')")
	List<Object[]> getMonthlyRentals();
}
