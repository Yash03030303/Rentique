package com.capgemini.equipment_rental.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capgemini.equipment_rental.entity.RentalItems;
import com.capgemini.equipment_rental.exceptions.RentalItemNotFoundException;
import com.capgemini.equipment_rental.repositories.RentalItemsRepository;

@ExtendWith(MockitoExtension.class)
public class RentalItemsServiceImplTest {

    @Mock
    private RentalItemsRepository rentalItemsRepository;

    @InjectMocks
    private RentalItemsServiceImpl rentalItemsService;

    private RentalItems testRentalItem;

    @BeforeEach
    void setUp() {
        testRentalItem = new RentalItems();
        testRentalItem.setRentalItemId(1L);
        testRentalItem.setQuantity(5L);
        testRentalItem.setDaysRented(10L);
    }

    @Test
    void createRentalItem_Success() {
        when(rentalItemsRepository.save(any(RentalItems.class))).thenReturn(testRentalItem);

        RentalItems createdItem = rentalItemsService.createRentalItem(testRentalItem);

        assertNotNull(createdItem);
        assertEquals(1L, createdItem.getRentalItemId());
        verify(rentalItemsRepository, times(1)).save(testRentalItem);
    }

    @Test
    void getRentalItemById_Found() {
        when(rentalItemsRepository.findById(1L)).thenReturn(Optional.of(testRentalItem));

        RentalItems foundItem = rentalItemsService.getRentalItemById(1L);

        assertNotNull(foundItem);
        assertEquals(1L, foundItem.getRentalItemId());
        verify(rentalItemsRepository, times(1)).findById(1L);
    }

    @Test
    void getRentalItemById_NotFound() {
        when(rentalItemsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RentalItemNotFoundException.class, () -> 
            rentalItemsService.getRentalItemById(1L)
        );
        verify(rentalItemsRepository, times(1)).findById(1L);
    }

    @Test
    void getAllRentalItems_Success() {
        List<RentalItems> itemsList = Arrays.asList(testRentalItem);
        when(rentalItemsRepository.findAll()).thenReturn(itemsList);

        List<RentalItems> result = rentalItemsService.getAllRentalItems();

        assertEquals(1, result.size());
        verify(rentalItemsRepository, times(1)).findAll();
    }

    @Test
    void updateRentalItem_Success() {
        RentalItems updatedItem = new RentalItems();
        updatedItem.setQuantity(3L);
        updatedItem.setDaysRented(7L);

        when(rentalItemsRepository.findById(1L)).thenReturn(Optional.of(testRentalItem));
        when(rentalItemsRepository.save(any(RentalItems.class))).thenReturn(testRentalItem);

        RentalItems result = rentalItemsService.updateRentalItem(1L, updatedItem);

        assertEquals(3L, result.getQuantity());
        assertEquals(7L, result.getDaysRented());
        verify(rentalItemsRepository, times(1)).findById(1L);
        verify(rentalItemsRepository, times(1)).save(testRentalItem);
    }

    @Test
    void deleteRentalItem_Success() {
        when(rentalItemsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(rentalItemsRepository).deleteById(1L);

        rentalItemsService.deleteRentalItem(1L);

        verify(rentalItemsRepository, times(1)).existsById(1L);
        verify(rentalItemsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRentalItem_NotFound() {
        when(rentalItemsRepository.existsById(1L)).thenReturn(false);

        assertThrows(RentalItemNotFoundException.class, () -> 
            rentalItemsService.deleteRentalItem(1L)
        );
        verify(rentalItemsRepository, times(1)).existsById(1L);
        verify(rentalItemsRepository, never()).deleteById(1L);
    }
}