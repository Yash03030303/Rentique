package com.capgemini.equipment_rental.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Returns")
public class Returns {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "returnId")
	private Long returnId;

	@NotNull(message = "Rental ID is required")
	@Column(name = "rentalId", nullable = false)
	private Long rentalId;

	@NotNull(message = "Return Date is required")
	@Column(name = "returnDate", nullable = false)
	private LocalDate returnDate;

	@Column(name = "itemCondition", length = 255)
	private String itemCondition;

	@Column(name = "lateFee", precision = 10, scale = 2)
	private BigDecimal lateFee;

	// Default constructor
	public Returns() {
	}

	// Constructor with parameters
	public Returns(Long returnId, Long rentalId, LocalDate returnDate, String itemCondition, BigDecimal lateFee) {
		this.returnId = returnId;
		this.rentalId = rentalId;
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

	public Long getRentalId() {
		return rentalId;
	}

	public void setRentalId(Long rentalId) {
		this.rentalId = rentalId;
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
		return "Returns [returnId=" + returnId + ", rentalId=" + rentalId + ", returnDate=" + returnDate
				+ ", itemCondition=" + itemCondition + ", lateFee=" + lateFee + "]";
	}
}
