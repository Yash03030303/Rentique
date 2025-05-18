package com.capgemini.equipment_rental.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Returns;
import com.capgemini.equipment_rental.exceptions.ReturnNotFoundException;
import com.capgemini.equipment_rental.repositories.ReturnsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReturnsServiceImpl implements ReturnsService {

    private final ReturnsRepository returnsRepository;

    @Autowired
    public ReturnsServiceImpl(ReturnsRepository returnsRepository) {
        this.returnsRepository = returnsRepository;
    }

    @Override
    public Returns createReturn(Returns returns) {
        log.info("Attempting to create a return record for Rental ID: {}", returns.getRental() != null ? returns.getRental().getRentalId() : "null");
        Returns savedReturn = returnsRepository.save(returns);
        log.info("Return record created with ID: {}", savedReturn.getReturnId());
        return savedReturn;
    }

    @Override
    public Returns getReturnById(Long returnId) {
        log.info("Fetching return with ID: {}", returnId);
        return returnsRepository.findById(returnId).orElseThrow(() -> {
            log.warn("Return not found with ID: {}", returnId);
            return new ReturnNotFoundException("Return with ID " + returnId + " not found.");
        });
    }

    @Override
    public List<Returns> getAllReturns() {
        log.info("Fetching all return records");
        List<Returns> allReturns = returnsRepository.findAll();
        log.debug("Number of return records found: {}", allReturns.size());
        return allReturns;
    }

    @Override
    public Returns updateReturn(Long returnId, Returns updatedReturn) {
        log.info("Attempting to update return with ID: {}", returnId);
        Returns existingReturn = getReturnById(returnId);

        log.debug("Updating return fields for ID: {}", returnId);
        existingReturn.setReturnDate(updatedReturn.getReturnDate());
        existingReturn.setItemCondition(updatedReturn.getItemCondition());
        existingReturn.setLateFee(updatedReturn.getLateFee());

        Returns savedReturn = returnsRepository.save(existingReturn);
        log.info("Return with ID {} updated successfully", returnId);
        return savedReturn;
    }

    @Override
    public void deleteReturn(Long returnId) {
        log.info("Attempting to delete return with ID: {}", returnId);

        if (!returnsRepository.existsById(returnId)) {
            log.warn("Return not found with ID: {}", returnId);
            throw new ReturnNotFoundException("Return with ID " + returnId + " not found.");
        }

        returnsRepository.deleteById(returnId);
        log.info("Return with ID {} deleted successfully", returnId);
    }
    
    @Override
    public Page<Returns> getReturnsByConditionAndDateRange(String itemCondition, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return returnsRepository.findByItemConditionContainingIgnoreCaseAndReturnDateBetween(itemCondition, startDate, endDate, pageable);
    }

}
