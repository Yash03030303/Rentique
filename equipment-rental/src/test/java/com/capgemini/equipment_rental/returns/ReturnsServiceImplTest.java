package com.capgemini.equipment_rental.returns;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capgemini.equipment_rental.entity.Returns;
import com.capgemini.equipment_rental.exceptions.ReturnNotFoundException;
import com.capgemini.equipment_rental.repositories.ReturnsRepository;
import com.capgemini.equipment_rental.services.ReturnsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReturnsServiceImplTest {

    @Mock
    private ReturnsRepository returnsRepository;

    @InjectMocks
    private ReturnsServiceImpl returnsService;

    private Returns testReturn;

    @BeforeEach
    void setUp() {
        testReturn = new Returns();
        testReturn.setReturnId(1L);
        testReturn.setReturnDate(LocalDate.now());
        testReturn.setItemCondition("Good");
        testReturn.setLateFee(BigDecimal.valueOf(0.0));
    }

    @Test
    void createReturn_Success() {
        when(returnsRepository.save(any(Returns.class))).thenReturn(testReturn);

        Returns created = returnsService.createReturn(testReturn);
        
        assertNotNull(created);
        assertEquals(1L, created.getReturnId());
        assertEquals("Good", created.getItemCondition());
        verify(returnsRepository, times(1)).save(any(Returns.class));
    }

    @Test
    void getReturnById_Success() {
        when(returnsRepository.findById(1L)).thenReturn(Optional.of(testReturn));

        Returns found = returnsService.getReturnById(1L);
        
        assertNotNull(found);
        assertEquals(1L, found.getReturnId());
        verify(returnsRepository, times(1)).findById(1L);
    }

    @Test
    void getReturnById_NotFound() {
        when(returnsRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ReturnNotFoundException.class, () -> {
            returnsService.getReturnById(99L);
        });
        
        verify(returnsRepository, times(1)).findById(99L);
    }

    @Test
    void getAllReturns_Success() {
        List<Returns> returnsList = Arrays.asList(testReturn);
        when(returnsRepository.findAll()).thenReturn(returnsList);

        List<Returns> found = returnsService.getAllReturns();
        
        assertNotNull(found);
        assertEquals(1, found.size());
        assertEquals(1L, found.get(0).getReturnId());
        verify(returnsRepository, times(1)).findAll();
    }

    @Test
    void updateReturn_Success() {
        Returns updatedReturn = new Returns();
        updatedReturn.setReturnDate(LocalDate.now().minusDays(1));
        updatedReturn.setItemCondition("Damaged");
        updatedReturn.setLateFee(BigDecimal.valueOf(10.0));
        
        when(returnsRepository.findById(1L)).thenReturn(Optional.of(testReturn));
        when(returnsRepository.save(any(Returns.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Returns result = returnsService.updateReturn(1L, updatedReturn);
        
        assertNotNull(result);
        assertEquals("Damaged", result.getItemCondition());
        assertEquals(BigDecimal.valueOf(10.0), result.getLateFee());
        verify(returnsRepository, times(1)).findById(1L);
        verify(returnsRepository, times(1)).save(any(Returns.class));
    }

    @Test
    void updateReturn_NotFound() {
        Returns updatedReturn = new Returns();
        updatedReturn.setItemCondition("Damaged");
        
        when(returnsRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(ReturnNotFoundException.class, () -> {
            returnsService.updateReturn(99L, updatedReturn);
        });
        
        verify(returnsRepository, times(1)).findById(99L);
        verify(returnsRepository, never()).save(any(Returns.class));
    }

    @Test
    void deleteReturn_Success() {
        when(returnsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(returnsRepository).deleteById(1L);
        
        returnsService.deleteReturn(1L);
        
        verify(returnsRepository, times(1)).existsById(1L);
        verify(returnsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteReturn_NotFound() {
        when(returnsRepository.existsById(99L)).thenReturn(false);
        
        assertThrows(ReturnNotFoundException.class, () -> {
            returnsService.deleteReturn(99L);
        });
        
        verify(returnsRepository, times(1)).existsById(99L);
        verify(returnsRepository, never()).deleteById(any());
    }
}