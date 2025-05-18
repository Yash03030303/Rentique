package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.exceptions.EmailAlreadyExistsException;
import com.capgemini.equipment_rental.exceptions.UserAlreadyExistsException;
import com.capgemini.equipment_rental.exceptions.UserNotFoundException;
import com.capgemini.equipment_rental.repositories.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

	private UsersRepository usersRepository;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}

	@Override
	public Users createUser(Users user) {
		log.info("Attempting to create user with email: {}", user.getEmail());

		if (usersRepository.existsByEmail(user.getEmail())) {
			log.warn("Email already in use: {}", user.getEmail());
			throw new EmailAlreadyExistsException("Email already in use: " + user.getEmail());
		}

		if (user.getUserId() != null && usersRepository.existsById(user.getUserId())) {
			log.warn("User with ID {} already exists.", user.getUserId());
			throw new UserAlreadyExistsException("User with ID " + user.getUserId() + " already exists.");
		}

		log.debug("Saving new user to the repository");
		return usersRepository.save(user);
	}

	@Override
	public Users getUserById(Long userId) {
		log.info("Fetching user by ID: {}", userId);
		return usersRepository.findById(userId).orElseThrow(() -> {
			log.warn("User not found with ID: {}", userId);
			return new UserNotFoundException("User with ID " + userId + " not found.");
		});
	}

	@Override
	public List<Users> getAllUsers() {
		log.info("Fetching all users from the repository");
		return usersRepository.findAll();
	}

	@Override
	public Users updateUser(Long userId, Users updatedUser) {
		log.info("Attempting to update user with ID: {}", userId);
		Users existingUser = getUserById(userId);

		if (!existingUser.getEmail().equals(updatedUser.getEmail())
				&& usersRepository.existsByEmail(updatedUser.getEmail())) {
			log.warn("Email already in use: {}", updatedUser.getEmail());
			throw new EmailAlreadyExistsException("Email already in use: " + updatedUser.getEmail());
		}

		log.debug("Updating fields for user ID: {}", userId);
		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setPhone(updatedUser.getPhone());
		existingUser.setUserType(updatedUser.getUserType());

		log.debug("Saving updated user to the repository");
		return usersRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long userId) {
		log.info("Attempting to delete user with ID: {}", userId);

		if (!usersRepository.existsById(userId)) {
			log.warn("User not found with ID: {}", userId);
			throw new UserNotFoundException("User with ID " + userId + " does not exist.");
		}

		log.debug("Deleting user from repository");
		usersRepository.deleteById(userId);
		log.info("User with ID: {} deleted successfully", userId);
	}

	@Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }
}
