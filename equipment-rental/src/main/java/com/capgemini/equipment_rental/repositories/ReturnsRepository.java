package com.capgemini.equipment_rental.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Returns;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Long> {
//	 @Query("SELECT COUNT(ret) FROM Return ret WHERE ret.rental.user.id = :userId AND ret.dueDate < :today AND ret.status <> 'RETURNED'")
//	    int countOverdueReturns(@Param("userId") Long userId, @Param("today") LocalDate today);
//	 
//	 @Query("SELECT COUNT(ret) FROM Return ret WHERE ret.rental.user.id = :userId AND ret.dueDate BETWEEN :today AND :upcoming AND ret.status <> 'RETURNED'")
//	    int countUpcomingReturns(@Param("userId") Long userId, @Param("today") LocalDate today, @Param("upcoming") LocalDate upcoming);

}
