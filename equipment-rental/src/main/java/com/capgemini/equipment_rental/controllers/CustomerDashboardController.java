package com.capgemini.equipment_rental.controllers;

import com.capgemini.equipment_rental.dto.CustomerDashboardDTO;
import com.capgemini.equipment_rental.services.CustomerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class CustomerDashboardController {

    private final CustomerDashboardService customerDashboardService;

    @Autowired
    public CustomerDashboardController(CustomerDashboardService customerDashboardService) {
        this.customerDashboardService = customerDashboardService;
    }

    @GetMapping("/customer/{userId}")
    public CustomerDashboardDTO getCustomerDashboard(@PathVariable Long userId) {
        return customerDashboardService.getCustomerDashboard(userId);
    }
}
