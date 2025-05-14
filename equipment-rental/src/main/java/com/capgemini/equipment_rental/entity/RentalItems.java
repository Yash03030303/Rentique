package com.capgemini.equipment_rental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "RentalItems")
public class RentalItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rentalItemId")
	private Long rentalItemId;

	@NotNull(message = "Rental ID is required")
	@Column(name = "rentalId", nullable = false)
	private Long rentalId;

	@NotNull(message = "Equipment ID is required")
	@Column(name = "equipmentId", nullable = false)
	private Long equipmentId;

	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	@Column(name = "quantity", nullable = false)
	private Long quantity;

	@NotNull(message = "Days rented is required")
	@Min(value = 1, message = "Days rented must be at least 1")
	@Column(name = "daysRented", nullable = false)
	private Long daysRented;

	// Default constructor
	public RentalItems() {
	}

	// Constructor with fields
	public RentalItems(Long rentalItemId, Long rentalId, Long equipmentId, Long quantity, Long daysRented) {
		this.rentalItemId = rentalItemId;
		this.rentalId = rentalId;
		this.equipmentId = equipmentId;
		this.quantity = quantity;
		this.daysRented = daysRented;
	}

	// Getters and Setters
	public Long getRentalItemId() {
		return rentalItemId;
	}

	public void setRentalItemId(Long rentalItemId) {
		this.rentalItemId = rentalItemId;
	}

	public Long getRentalId() {
		return rentalId;
	}

	public void setRentalId(Long rentalId) {
		this.rentalId = rentalId;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
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

	@Override
	public String toString() {
		return "RentalItems [rentalItemId=" + rentalItemId + ", rentalId=" + rentalId + ", equipmentId=" + equipmentId
				+ ", quantity=" + quantity + ", daysRented=" + daysRented + "]";
	}
}