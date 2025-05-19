package com.capgemini.equipment_rental.services;

import com.capgemini.equipment_rental.dto.CustomerDashboardDTO;

public interface CustomerDashboardService {
    CustomerDashboardDTO getCustomerDashboard(Long userId);
}

