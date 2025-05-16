package com.capgemini.equipment_rental.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String email);


	boolean existsByEmail(String email);
}
