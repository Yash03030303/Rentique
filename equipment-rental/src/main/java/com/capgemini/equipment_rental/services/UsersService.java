package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.entity.Users;

public interface UsersService {
	Users createUser(Users user);

	Users getUserById(Long userId);


	List<Users> getAllUsers();

	Users updateUser(Long userId, Users user);

	void deleteUser(Long userId);
}
