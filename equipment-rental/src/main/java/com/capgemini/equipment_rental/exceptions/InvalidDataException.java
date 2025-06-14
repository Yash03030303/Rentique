package com.capgemini.equipment_rental.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidDataException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public InvalidDataException(String message) {
		super(message);
	}
}