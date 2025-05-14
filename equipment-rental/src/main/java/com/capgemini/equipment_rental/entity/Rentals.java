package com.capgemini.equipment_rental.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Rentals")
public class Rentals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalId")
    private Long rentalId;

    @NotNull(message = "Rental Date is required")
    @Column(name = "rentalDate", nullable = false)
    private LocalDate rentalDate;

    @NotNull(message = "Due Date is required")
    @Column(name = "dueDate", nullable = false)
    private LocalDate dueDate;

    @NotNull(message = "Amount is required")
    @Column(name = "totalAmount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "UserID is required")
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @JsonManagedReference
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalItems> rentalItems;

    public Rentals() {}

    public Rentals(Long rentalId, LocalDate rentalDate, LocalDate dueDate, BigDecimal totalAmount, Users user, List<RentalItems> rentalItems) {
        this.rentalId = rentalId;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.user = user;
        this.rentalItems = rentalItems;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<RentalItems> getRentalItems() {
        return rentalItems;
    }

    public void setRentalItems(List<RentalItems> rentalItems) {
        this.rentalItems = rentalItems;
    }

    @Override
    public String toString() {
        return "Rentals [rentalId=" + rentalId + ", rentalDate=" + rentalDate + ", dueDate=" + dueDate
                + ", totalAmount=" + totalAmount + ", userId=" + (user != null ? user.getUserId() : null) + "]";
    }

}