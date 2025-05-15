package com.capgemini.equipment_rental.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentalNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RentalNotFoundException(String message) {
		super(message);
	}
}
