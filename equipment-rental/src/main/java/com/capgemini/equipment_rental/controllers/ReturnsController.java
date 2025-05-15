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
import com.capgemini.equipment_rental.services.ReturnsService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/returns")
@Slf4j
public class ReturnsController {

	private final ReturnsService returnsService;

	@Autowired
	public ReturnsController(ReturnsService returnsService) {
		this.returnsService = returnsService;
	}

	// Get all returns
	@GetMapping
	public ResponseEntity<List<Returns>> getAllReturns() {
		log.info("Received request to fetch all returns");
		List<Returns> returnsList = returnsService.getAllReturns();
		log.debug("Returning {} returns", returnsList.size());
		return ResponseEntity.ok(returnsList);
	}

	// Get return by ID
	@GetMapping("/{id}")
	public ResponseEntity<Returns> getReturnById(@PathVariable Long id) {
		log.info("Received request to fetch return with ID: {}", id);
		Returns returns = returnsService.getReturnById(id);
		log.debug("Retuerns fetched: {}", returns);
		return ResponseEntity.ok(returns);
	}

	// Update return
	@PutMapping("/{id}")
	public ResponseEntity<Returns> updateReturn(@PathVariable Long id, @Valid @RequestBody Returns updatedReturn, BindingResult result) {
		log.info("Received request to update return with ID: {}", id);
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Returns updated = returnsService.updateReturn(id, updatedReturn);
		log.debug("Return update with ID: {}", updatedReturn.getReturnId());
		return ResponseEntity.ok(updated);
	}

	// Delete return
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReturn(@PathVariable Long id) {
		log.info("Received request to delete returns with ID: {}", id); 
		returnsService.deleteReturn(id);
		log.info("Returns with ID {} successfully deleted", id);
		return ResponseEntity.noContent().build();
	}
}
