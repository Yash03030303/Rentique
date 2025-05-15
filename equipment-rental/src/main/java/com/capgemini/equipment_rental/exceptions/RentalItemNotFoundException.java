package com.capgemini.equipment_rental.exceptions;

public class RentalItemNotFoundException extends RuntimeException {
    public RentalItemNotFoundException(String message) {
        super(message);
    }
}
