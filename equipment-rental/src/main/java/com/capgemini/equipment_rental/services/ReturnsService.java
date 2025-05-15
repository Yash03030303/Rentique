package com.capgemini.equipment_rental.services;

import java.util.List;

import com.capgemini.equipment_rental.entity.Returns;

public interface ReturnsService {

    Returns createReturn(Returns returns);

    Returns getReturnById(Long returnId);

    List<Returns> getAllReturns();

    Returns updateReturn(Long returnId, Returns updatedReturn);

    void deleteReturn(Long returnId);
}
