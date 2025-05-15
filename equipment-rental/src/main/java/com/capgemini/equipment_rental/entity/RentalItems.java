package com.capgemini.equipment_rental.entity;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "RentalItems")
public class RentalItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_item_id")
    private Long rentalItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity")
    private Long quantity;

    @NotNull(message = "Days rented is required")
    @Min(value = 1, message = "Days rented must be at least 1")
    @Column(name = "days_rented")
    private Long daysRented;

    @NotNull(message = "Rental reference is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    @JsonBackReference(value = "rental-rentalItems")
    private Rentals rental;

    @NotNull(message = "Equipment reference is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "equipment-rentalItems")
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    public RentalItems() {}

    public RentalItems(Long rentalItemId, Long quantity, Long daysRented, Rentals rental, Equipment equipment) {
        this.rentalItemId = rentalItemId;
        this.quantity = quantity;
        this.daysRented = daysRented;
        this.rental = rental;
        this.equipment = equipment;
    }

    public Long getRentalItemId() {
        return rentalItemId;
    }

    public void setRentalItemId(Long rentalItemId) {
        this.rentalItemId = rentalItemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(Long daysRented) {
        this.daysRented = daysRented;
    }

    public Rentals getRental() {
        return rental;
    }

    public void setRental(Rentals rental) {
        this.rental = rental;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "RentalItems [rentalItemId=" + rentalItemId +
               ", quantity=" + quantity +
               ", daysRented=" + daysRented +
               ", rentalId=" + (rental != null ? rental.getRentalId() : null) +
               ", equipmentId=" + (equipment != null ? equipment.getEquipmentId() : null) + "]";
    }
}