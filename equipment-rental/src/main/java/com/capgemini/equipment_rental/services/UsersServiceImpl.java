package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.exceptions.EmailAlreadyExistsException;
import com.capgemini.equipment_rental.exceptions.UserAlreadyExistsException;
import com.capgemini.equipment_rental.exceptions.UserNotFoundException;
import com.capgemini.equipment_rental.repositories.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

	private UsersRepository usersRepository;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}

	@Override
	public Users createUser(Users user) {
		if (usersRepository.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("Email already in use: " + user.getEmail());
		}

		if (user.getUserId() != null && usersRepository.existsById(user.getUserId())) {
			throw new UserAlreadyExistsException("User with ID " + user.getUserId() + " already exists.");
		}

		return usersRepository.save(user);
	}

	@Override
	public Users getUserById(Long userId) {
		return usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
	}

	@Override
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}

	@Override
	public Users updateUser(Long userId, Users updatedUser) {
		Users existingUser = getUserById(userId);

		if (!existingUser.getEmail().equals(updatedUser.getEmail())
				&& usersRepository.existsByEmail(updatedUser.getEmail())) {
			throw new EmailAlreadyExistsException("Email already in use: " + updatedUser.getEmail());
		}

		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setPassword(updatedUser.getPassword());
		existingUser.setPhone(updatedUser.getPhone());
		existingUser.setUserType(updatedUser.getUserType());

		return usersRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long userId) {
		if (!usersRepository.existsById(userId)) {
			throw new UserNotFoundException("User with ID " + userId + " does not exist.");
		}

		usersRepository.deleteById(userId);
	}

	@Override
	public Users findByEmail(String email) {
		return usersRepository.findByEmail( email)
				.orElseThrow(()->new UserNotFoundException(" Email not Found !"));
	}


	@Override
	public boolean existsByEmail(String email) {
		return usersRepository.existsByEmail(email);
	}
}
