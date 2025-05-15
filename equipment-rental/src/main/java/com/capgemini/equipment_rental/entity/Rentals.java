package com.capgemini.equipment_rental.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "Rentals")
public class Rentals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_id")
	private Long rentalId;

	@NotNull(message = "Rental Date is required")
	@PastOrPresent(message = "Rental Date cannot be in the future")
	@Column(name = "rental_date")
	private LocalDate rentalDate;

	@NotNull(message = "Due Date is required")
	@Future(message = "Due Date must be in the future")
	@Column(name = "due_date")
	private LocalDate dueDate;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", inclusive = false, message = "Total amount must be greater than 0")
	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@NotNull(message = "UserID is required")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@JsonManagedReference(value = "rental-returns")
	@OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
	private List<Returns> returns;

	@JsonManagedReference(value = "rental-rentalItems")
	@OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RentalItems> rentalItems;

	public Rentals(Long rentalId,
			@NotNull(message = "Rental Date is required") @PastOrPresent(message = "Rental Date cannot be in the future") LocalDate rentalDate,
			@NotNull(message = "Due Date is required") @Future(message = "Due Date must be in the future") LocalDate dueDate,
			@NotNull(message = "Amount is required") @DecimalMin(value = "0.01", inclusive = false, message = "Total amount must be greater than 0") BigDecimal totalAmount,
			@NotNull(message = "UserID is required") Users user, List<Returns> returns, List<RentalItems> rentalItems) {
		super();
		this.rentalId = rentalId;
		this.rentalDate = rentalDate;
		this.dueDate = dueDate;
		this.totalAmount = totalAmount;
		this.user = user;
		this.returns = returns;
		this.rentalItems = rentalItems;
	}

	public Rentals() {
		super();
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

	public List<Returns> getReturns() {
		return returns;
	}

	public void setReturns(List<Returns> returns) {
		this.returns = returns;
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
				+ ", totalAmount=" + totalAmount + ", user=" + user + ", returns=" + returns + ", rentalItems="
				+ rentalItems + "]";
	}
}
