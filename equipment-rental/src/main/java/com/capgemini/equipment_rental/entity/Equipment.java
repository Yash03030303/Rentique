package com.capgemini.equipment_rental.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Column(name = "equipment_id")
    private Long equipmentId;

    @NotBlank(message = "Equipment name is required")
    @Size(max = 100, message = "Name can have up to 100 characters")
    @Column(name = "name", length = 100)
    private String name;

    @NotNull(message = "Rental price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rental price must be greater than 0")
    @Column(name = "rental_price_per_day", precision = 10, scale = 2)
    private BigDecimal rentalPricePerDay;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    @Column(name = "stock")
    private Long stock;

    @NotNull(message = "Category is required")
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "category-equipment")
    private Categories categories;

    // Optional: OneToMany for reverse relationship
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "equipment-rentalItems")
    private List<RentalItems> rentalItems;

    public Equipment() {}

    public Equipment(Long equipmentId, String name, BigDecimal rentalPricePerDay, Long stock, Categories categories) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.rentalPricePerDay = rentalPricePerDay;
        this.stock = stock;
        this.categories = categories;
    }

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

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public List<RentalItems> getRentalItems() {
        return rentalItems;
    }

    public void setRentalItems(List<RentalItems> rentalItems) {
        this.rentalItems = rentalItems;
    }

    @Override
    public String toString() {
        return "Equipment [equipmentId=" + equipmentId +
               ", name=" + name +
               ", rentalPricePerDay=" + rentalPricePerDay +
               ", stock=" + stock +
               ", category=" + (categories != null ? categories.getCategoryId() : null) + "]";
    }
}
