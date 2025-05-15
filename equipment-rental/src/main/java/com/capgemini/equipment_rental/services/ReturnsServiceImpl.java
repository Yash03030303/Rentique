package com.capgemini.equipment_rental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Returns;
import com.capgemini.equipment_rental.exceptions.ReturnNotFoundException;
import com.capgemini.equipment_rental.repositories.ReturnsRepository;

@Service
public class ReturnsServiceImpl implements ReturnsService {

   
    private ReturnsRepository returnsRepository;
    
    @Autowired
    public ReturnsServiceImpl(ReturnsRepository returnsRepository) {
		super();
		this.returnsRepository = returnsRepository;
	}

	@Override
    public Returns createReturn(Returns returns) {
        return returnsRepository.save(returns);
    }

    @Override
    public Returns getReturnById(Long returnId) {
        return returnsRepository.findById(returnId)
                .orElseThrow(() -> new ReturnNotFoundException("Return with ID " + returnId + " not found."));
    }

    @Override
    public List<Returns> getAllReturns() {
        return returnsRepository.findAll();
    }

    @Override
    public Returns updateReturn(Long returnId, Returns updatedReturn) {
        Returns existingReturn = getReturnById(returnId);

        existingReturn.setReturnDate(updatedReturn.getReturnDate());
        existingReturn.setItemCondition(updatedReturn.getItemCondition());
        existingReturn.setLateFee(updatedReturn.getLateFee());

        return returnsRepository.save(existingReturn);
    }

    @Override
    public void deleteReturn(Long returnId) {
        if (!returnsRepository.existsById(returnId)) {
            throw new ReturnNotFoundException("Return with ID " + returnId + " not found.");
        }
        returnsRepository.deleteById(returnId);
    }
}
