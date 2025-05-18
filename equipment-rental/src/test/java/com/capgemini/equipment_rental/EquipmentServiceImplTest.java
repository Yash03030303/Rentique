package com.capgemini.equipment_rental;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.equipment_rental.entity.Categories;
import com.capgemini.equipment_rental.entity.Equipment;
import com.capgemini.equipment_rental.exceptions.EquipmentNotFoundException;
import com.capgemini.equipment_rental.repositories.EquipmentRepository;
import com.capgemini.equipment_rental.services.EquipmentServiceImpl;

@SpringBootTest
class EquipmentServiceImplTest {

	@Mock
	private EquipmentRepository equipmentRepository;

	@InjectMocks
	private EquipmentServiceImpl equipmentService;

	private Equipment equipment;

	@BeforeEach
	void setup() {
		Categories category = new Categories();
		category.setCategoryId(1L);

		equipment = new Equipment(1L, "Hammer", new BigDecimal("15.00"), 10L, category);
	}

	@Test
	void testCreateEquipment() {
		when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

		Equipment saved = equipmentService.createEquipment(equipment);

		assertNotNull(saved);
		assertEquals("Hammer", saved.getName());
	}

	@Test
	void testGetEquipmentById_Exists() {
		when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));

		Equipment found = equipmentService.getEquipmentById(1L);

		assertEquals(1L, found.getEquipmentId());
		assertEquals("Hammer", found.getName());
	}

	@Test
	void testGetEquipmentById_NotExists() {
		when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(EquipmentNotFoundException.class, () -> {
			equipmentService.getEquipmentById(1L);
		});
	}

	@Test
	void testGetAllEquipment() {
		when(equipmentRepository.findAll()).thenReturn(Collections.singletonList(equipment));

		List<Equipment> equipmentList = equipmentService.getAllEquipment();

		assertEquals(1, equipmentList.size());
	}

	@Test
	void testUpdateEquipment() {
		Equipment updated = new Equipment(1L, "Drill", new BigDecimal("20.00"), 5L, equipment.getCategories());

		when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
		when(equipmentRepository.save(any(Equipment.class))).thenReturn(updated);

		Equipment result = equipmentService.updateEquipment(1L, updated);

		assertEquals("Drill", result.getName());
		assertEquals(new BigDecimal("20.00"), result.getRentalPricePerDay());
	}

	@Test
	void testDeleteEquipment_Exists() {
		when(equipmentRepository.existsById(1L)).thenReturn(true);

		assertDoesNotThrow(() -> equipmentService.deleteEquipment(1L));
		verify(equipmentRepository).deleteById(1L);
	}

	@Test
	void testDeleteEquipment_NotExists() {
		when(equipmentRepository.existsById(1L)).thenReturn(false);

		assertThrows(EquipmentNotFoundException.class, () -> equipmentService.deleteEquipment(1L));
	}
}
