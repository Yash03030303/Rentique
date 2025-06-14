package com.capgemini.equipment_rental.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping
	public ResponseEntity<Returns> createReturn(@Valid @RequestBody Returns returns, BindingResult result) {
		log.info("Received request to create a return");
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Returns createdReturn = returnsService.createReturn(returns);
		return ResponseEntity.ok(createdReturn);
	}

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

	// Delete return
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReturn(@PathVariable Long id) {
		log.info("Received request to delete returns with ID: {}", id);
		returnsService.deleteReturn(id);
		log.info("Returns with ID {} successfully deleted", id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Returns>> searchReturns(@RequestParam(defaultValue = "") String itemCondition,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "returnDate") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Returns> returnsPage = returnsService.getReturnsByConditionAndDateRange(itemCondition, startDate, endDate,
				pageable);
		return ResponseEntity.ok(returnsPage);
	}

}
