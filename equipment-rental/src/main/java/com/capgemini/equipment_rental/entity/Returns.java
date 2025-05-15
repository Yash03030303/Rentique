package com.capgemini.equipment_rental.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "Returns")
public class Returns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Long returnId;

    @NotNull(message = "Rental reference is required")
    @JsonBackReference(value = "rental-returns")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rentals rental;


    @NotNull(message = "Return date is required")
    @PastOrPresent(message = "Return date cannot be in the future")
    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "item_condition", length = 255)
    private String itemCondition;

    @DecimalMin(value = "0.0", message = "Late fee cannot be negative")
    @Column(name = "late_fee", precision = 10, scale = 2)
    private BigDecimal lateFee;

    // Constructors
    public Returns() {}

    public Returns(Long returnId, Rentals rental, LocalDate returnDate, String itemCondition, BigDecimal lateFee) {
        this.returnId = returnId;
        this.rental = rental;
        this.returnDate = returnDate;
        this.itemCondition = itemCondition;
        this.lateFee = lateFee;
    }

    // Getters and Setters
    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public Rentals getRental() {
        return rental;
    }

    public void setRental(Rentals rental) {
        this.rental = rental;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    @Override
    public String toString() {
        return "Returns [returnId=" + returnId +
               ", rentalId=" + (rental != null ? rental.getRentalId() : null) +
               ", returnDate=" + returnDate +
               ", itemCondition=" + itemCondition +
               ", lateFee=" + lateFee + "]";
    }
}