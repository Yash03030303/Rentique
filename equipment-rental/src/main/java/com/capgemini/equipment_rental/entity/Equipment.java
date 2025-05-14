package com.capgemini.equipment_rental.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Equipment")
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipmentId")
	private Long equipmentId;

	@NotBlank(message = "Equipment name is required")
	@Size(max = 100, message = "Name can have up to 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ManyToOne
	@JoinColumn(name = "categoryId", nullable = false)
	@NotNull(message = "Category is required")
	private Categories category; // Changed to a reference to Categories entity

	@NotNull(message = "Rental price is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Rental price must be greater than 0")
	@Column(name = "rentalPricePerDay", nullable = false, precision = 10, scale = 2)
	private BigDecimal rentalPricePerDay;

	@NotNull(message = "Stock is required")
	@Min(value = 0, message = "Stock cannot be negative")
	@Column(name = "stock", nullable = false, columnDefinition = "INT DEFAULT 0")
	private Long stock;

	// Constructors
	public Equipment() {
	}

	public Equipment(Long equipmentId, String name, Categories category, BigDecimal rentalPricePerDay, Long stock) {
		this.equipmentId = equipmentId;
		this.name = name;
		this.category = category;
		this.rentalPricePerDay = rentalPricePerDay;
		this.stock = stock;
	}

	// Getters and Setters
	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public BigDecimal getRentalPricePerDay() {
		return rentalPricePerDay;
	}

	public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
		this.rentalPricePerDay = rentalPricePerDay;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Equipment{" + "equipmentId=" + equipmentId + ", name='" + name + '\'' + ", category=" + category
				+ ", rentalPricePerDay=" + rentalPricePerDay + ", stock=" + stock + '}';
	}
}
