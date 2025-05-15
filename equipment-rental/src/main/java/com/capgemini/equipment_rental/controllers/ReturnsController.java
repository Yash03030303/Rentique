package com.capgemini.equipment_rental.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.equipment_rental.entity.Returns;
import com.capgemini.equipment_rental.services.RentalsService;
import com.capgemini.equipment_rental.services.ReturnsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/returns")
public class ReturnsController {

	private final ReturnsService returnsService;
	private final RentalsService rentalsService;

	@Autowired
	public ReturnsController(ReturnsService returnsService, RentalsService rentalsService) {
		this.returnsService = returnsService;
		this.rentalsService = rentalsService;
	}

	
	@GetMapping
	public ResponseEntity<List<Returns>> getAllReturns() {
		List<Returns> returnsList = returnsService.getAllReturns();
		return ResponseEntity.ok(returnsList);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Returns> getReturnById(@PathVariable Long id) {
		Returns returns = returnsService.getReturnById(id);
		return ResponseEntity.ok(returns);
	}


	@PutMapping("/{id}")
	public ResponseEntity<Returns> updateReturn(@PathVariable Long id, @Valid @RequestBody Returns updatedReturn, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid returns data: " + result.getAllErrors());
		}
		Returns updated = returnsService.updateReturn(id, updatedReturn);
		return ResponseEntity.ok(updated);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReturn(@PathVariable Long id) {
		returnsService.deleteReturn(id);
		return ResponseEntity.noContent().build();
	}
}
