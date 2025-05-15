package com.capgemini.equipment_rental;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.entity.Users.UserType;
import com.capgemini.equipment_rental.exceptions.EmailAlreadyExistsException;
import com.capgemini.equipment_rental.exceptions.UserAlreadyExistsException;
import com.capgemini.equipment_rental.exceptions.UserNotFoundException;
import com.capgemini.equipment_rental.repositories.UsersRepository;
import com.capgemini.equipment_rental.services.UsersServiceImpl;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

	@Mock
	private UsersRepository usersRepository;

	@InjectMocks
	private UsersServiceImpl usersService;

	private Users user;

	@BeforeEach
	void setUp() {
		user = new Users(1L, "John Doe", "john@example.com", "Password@123", "9123456789", UserType.USER);
	}

	@Test
	void createUser_ShouldReturnSavedUser() {
		when(usersRepository.existsByEmail(any())).thenReturn(false);
		when(usersRepository.save(any())).thenReturn(user);

		Users savedUser = usersService.createUser(user);

		assertNotNull(savedUser);
		assertEquals("John Doe", savedUser.getName());
		verify(usersRepository, times(1)).save(user);
	}

	@Test
	void createUser_ShouldThrowEmailExistsException() {
		when(usersRepository.existsByEmail(any())).thenReturn(true);

		assertThrows(EmailAlreadyExistsException.class, () -> usersService.createUser(user));
	}

	@Test
	void getUserById_ShouldReturnUser() {
		when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

		Users foundUser = usersService.getUserById(1L);

		assertNotNull(foundUser);
		assertEquals(1L, foundUser.getUserId());
	}

	@Test
	void getUserById_ShouldThrowNotFoundException() {
		when(usersRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> usersService.getUserById(1L));
	}

	@Test
	void updateUser_ShouldUpdateExistingUser() {
		Users updatedUser = new Users(1L, "Jane Doe", "jane@example.com", "NewPass@123", "9876543210",
				UserType.SUPPLIER);

		when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
		when(usersRepository.save(any())).thenReturn(updatedUser);

		Users result = usersService.updateUser(1L, updatedUser);

		assertEquals("Jane Doe", result.getName());
		assertEquals("jane@example.com", result.getEmail());
		verify(usersRepository, times(1)).save(user);
	}

	@Test
	void deleteUser_ShouldCallDeleteMethod() {
		when(usersRepository.existsById(1L)).thenReturn(true);
		doNothing().when(usersRepository).deleteById(1L);

		usersService.deleteUser(1L);

		verify(usersRepository, times(1)).deleteById(1L);
	}
}