package com.capgemini.equipment_rental.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserAnalytics {
	
	 	private BigDecimal totalAmountSpent;
	    private BigDecimal averageRentalCost;
	    private List<MonthlySpending> monthlyBreakdown;
	    private List<MostRentedEquipment> topRentedEquipment;
	    private LongestRental longestRental;
		
	    public BigDecimal getTotalAmountSpent() {
			return totalAmountSpent;
		}
		public void setTotalAmountSpent(BigDecimal totalAmountSpent) {
			this.totalAmountSpent = totalAmountSpent;
		}
		public BigDecimal getAverageRentalCost() {
			return averageRentalCost;
		}
		public void setAverageRentalCost(BigDecimal averageRentalCost) {
			this.averageRentalCost = averageRentalCost;
		}
		public List<MonthlySpending> getMonthlyBreakdown() {
			return monthlyBreakdown;
		}
		public void setMonthlyBreakdown(List<MonthlySpending> monthlyBreakdown) {
			this.monthlyBreakdown = monthlyBreakdown;
		}
		public List<MostRentedEquipment> getTopRentedEquipment() {
			return topRentedEquipment;
		}
		public void setTopRentedEquipment(List<MostRentedEquipment> topRentedEquipment) {
			this.topRentedEquipment = topRentedEquipment;
		}
		public LongestRental getLongestRental() {
			return longestRental;
		}
		public void setLongestRental(LongestRental longestRental) {
			this.longestRental = longestRental;
		}
	    
	    
	    
}
