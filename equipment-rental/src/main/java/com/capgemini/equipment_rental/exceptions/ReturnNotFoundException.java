package com.capgemini.equipment_rental.exceptions;

public class ReturnNotFoundException extends RuntimeException {
    public ReturnNotFoundException(String message) {
        super(message);
    }
}
