package com.capgemini.equipment_rental.rentals;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.capgemini.equipment_rental.entity.Rentals;
import com.capgemini.equipment_rental.exceptions.RentalNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalsRepository;
import com.capgemini.equipment_rental.services.RentalsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RentalsServiceImplTest {

	@Mock
	private RentalsRepository rentalsRepository;

	@InjectMocks
	private RentalsServiceImpl rentalsService;

	private Rentals sampleRental;

	@BeforeEach
	void setUp() {
		sampleRental = new Rentals();
		sampleRental.setRentalId(1L);
		sampleRental.setRentalDate(LocalDate.now());
		sampleRental.setDueDate(LocalDate.now().plusDays(2));
		sampleRental.setTotalAmount(new BigDecimal("100.00"));
	}

	@Test
	void getAllRentals_returnsList() {
		when(rentalsRepository.findAll()).thenReturn(Arrays.asList(sampleRental));
		List<Rentals> rentals = rentalsService.getAllRentals();
		assertThat(rentals).hasSize(1);
		verify(rentalsRepository, times(1)).findAll();
	}

	@Test
	void getRentalsById_found_returnsRental() {
		when(rentalsRepository.findById(1L)).thenReturn(Optional.of(sampleRental));
		Rentals found = rentalsService.getRentalsById(1L);
		assertThat(found.getRentalId()).isEqualTo(1L);
	}

	@Test
	void getRentalsById_notFound_throwsException() {
		when(rentalsRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(RentalNotFoundException.class, () -> rentalsService.getRentalsById(1L));
	}

	@Test
	void createRentals_savesAndReturnsRental() {
		when(rentalsRepository.save(any(Rentals.class))).thenReturn(sampleRental);
		Rentals saved = rentalsService.createRentals(sampleRental);
		assertThat(saved.getRentalId()).isEqualTo(1L);
	}

	@Test
	void updateRentals_found_updatesAndReturnsRental() {
		when(rentalsRepository.findById(1L)).thenReturn(Optional.of(sampleRental));
		when(rentalsRepository.save(any(Rentals.class))).thenReturn(sampleRental);
		Rentals updated = rentalsService.updateRentals(1L, sampleRental);
		assertThat(updated.getRentalId()).isEqualTo(1L);
	}

	@Test
	void updateRentals_notFound_throwsException() {
		when(rentalsRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(RentalNotFoundException.class, () -> rentalsService.updateRentals(1L, sampleRental));
	}

	@Test
	void deleteRentals_found_deletes() {
		when(rentalsRepository.existsById(1L)).thenReturn(true);
		doNothing().when(rentalsRepository).deleteById(1L);
		rentalsService.deleteRentals(1L);
		verify(rentalsRepository, times(1)).deleteById(1L);
	}

	@Test
	void deleteRentals_notFound_throwsException() {
		when(rentalsRepository.existsById(1L)).thenReturn(false);
		assertThrows(RentalNotFoundException.class, () -> rentalsService.deleteRentals(1L));
	}
}