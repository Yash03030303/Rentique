package com.capgemini.equipment_rental.dto;

import java.util.List;
import java.util.Map;

public class CustomerDashboardDTO {
 private int activeRentals;
 private int overdueReturns;
 private int totalRentals;
 private int upcomingReturns;
 private Map<String, Integer> rentalCategories;
 private List<Integer> monthlyRentals;
public int getActiveRentals() {
	return activeRentals;
}

public CustomerDashboardDTO(int activeRentals, int overdueReturns, int totalRentals, int upcomingReturns,
		Map<String, Integer> rentalCategories, List<Integer> monthlyRentals) {
	super();
	this.activeRentals = activeRentals;
	this.overdueReturns = overdueReturns;
	this.totalRentals = totalRentals;
	this.upcomingReturns = upcomingReturns;
	this.rentalCategories = rentalCategories;
	this.monthlyRentals = monthlyRentals;
}

public CustomerDashboardDTO() {
	super();
}

public void setActiveRentals(int activeRentals) {
	this.activeRentals = activeRentals;
}
public int getOverdueReturns() {
	return overdueReturns;
}
public void setOverdueReturns(int overdueReturns) {
	this.overdueReturns = overdueReturns;
}
public int getTotalRentals() {
	return totalRentals;
}
public void setTotalRentals(int totalRentals) {
	this.totalRentals = totalRentals;
}
public int getUpcomingReturns() {
	return upcomingReturns;
}
public void setUpcomingReturns(int upcomingReturns) {
	this.upcomingReturns = upcomingReturns;
}
public Map<String, Integer> getRentalCategories() {
	return rentalCategories;
}
public void setRentalCategories(Map<String, Integer> rentalCategories) {
	this.rentalCategories = rentalCategories;
}
public List<Integer> getMonthlyRentals() {
	return monthlyRentals;
}
public void setMonthlyRentals(List<Integer> monthlyRentals) {
	this.monthlyRentals = monthlyRentals;
}
 

}

