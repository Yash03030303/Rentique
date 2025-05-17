// AnalyticsController.java
package com.capgemini.equipment_rental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.equipment_rental.dto.UserAnalytics;
import com.capgemini.equipment_rental.services.RentalsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

//    private final RentalsService rentalsService;
//
//    public AnalyticsController(RentalsService rentalsService) {
//        this.rentalsService = rentalsService;
//    }
//
//    @GetMapping("/my")
//    public UserAnalytics getMyAnalytics(@AuthenticationPrincipal Long userId) {
//        return rentalsService.getUserAnalytics(userId);
//    }
	
	 @Autowired
	    private RentalsService rentalsService;

	    @GetMapping("/user/{userId}")
	    public ResponseEntity<UserAnalytics> getUserAnalytics(@PathVariable Long userId) {
	        return ResponseEntity.ok(rentalsService.getUserAnalytics(userId));
	    }
}