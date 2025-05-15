package com.capgemini.equipment_rental.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Categories")
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long categoryId;

	@NotBlank(message = "Category name is required")
	@Size(min = 2, max = 100, message = "Category name must have at least 2 characters")
	@Column(name = "name")
	private String name;

	@JsonManagedReference(value = "category-equipment")
	@OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Equipment> equipmentList;

	public Categories() {
	}

	public Categories(Long categoryId, String name, List<Equipment> equipmentList) {
		this.categoryId = categoryId;
		this.name = name;
		this.equipmentList = equipmentList;
	}

	// Getters and Setters
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	@Override
	public String toString() {
		return "Categories [categoryId=" + categoryId + ", name=" + name + ", equipmentList=" + equipmentList + "]";
	}
}
