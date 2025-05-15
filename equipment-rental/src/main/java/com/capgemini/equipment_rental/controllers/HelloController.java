package com.capgemini.equipment_rental.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
		@GetMapping("/user")
		@PreAuthorize("hasRole('USER') or hasRole('SUPPLIER')")
		public String userAccess() {
			return "Hello User!";
		}
		
		@GetMapping("/supplier")
		@PreAuthorize("hasRole('SUPPLIER')")
		public String supplierAccess() {
			return "Hello Supplier";
		}

		@GetMapping("/all")
		public String allAccess() {
			return "Hello Authenticated User!";
		}
}
