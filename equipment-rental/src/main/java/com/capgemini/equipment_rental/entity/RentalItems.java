package com.capgemini.equipment_rental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "RentalItems")
public class RentalItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalItemId")
    private Long rentalItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @NotNull(message = "Days rented is required")
    @Min(value = 1, message = "Days rented must be at least 1")
    @Column(name = "daysRented", nullable = false)
    private Long daysRented;

    @ManyToOne
    @JoinColumn(name = "rentalId", nullable = false)
    private Rentals rental;

    @NotNull(message = "Equipment ID is required")
    @Column(name = "equipmentId", nullable = false)
    private Long equipmentId;

    public RentalItems() {}

    public RentalItems(Long rentalItemId, Long quantity, Long daysRented, Rentals rental, Long equipmentId) {
        this.rentalItemId = rentalItemId;
        this.quantity = quantity;
        this.daysRented = daysRented;
        this.rental = rental;
        this.equipmentId = equipmentId;
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

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public String toString() {
        return "RentalItems [rentalItemId=" + rentalItemId + ", quantity=" + quantity + ", daysRented=" + daysRented
                + ", rental=" + (rental != null ? rental.getRentalId() : null) + ", equipmentId=" + equipmentId + "]";
    }
}