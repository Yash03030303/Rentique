package com.capgemini.equipment_rental.dto;

import java.util.Map;

public class DashboardDTO {
    private Long totalEquipment;
    private Long activeRentals;
    private Map<String, Long> equipmentDistribution;
    private Map<String, Long> monthlyRentals;

    // Constructors
    public DashboardDTO() {}
    
    public DashboardDTO(Long totalEquipment, Long activeRentals) {
        this.totalEquipment = totalEquipment;
        this.activeRentals = activeRentals;
    }
    
    // Getters & Setters
    public Long getTotalEquipment() { return totalEquipment; }
    public void setTotalEquipment(Long totalEquipment) { this.totalEquipment = totalEquipment; }
    
    public Long getActiveRentals() { return activeRentals; }
    public void setActiveRentals(Long activeRentals) { this.activeRentals = activeRentals; }
    
    public Map<String, Long> getEquipmentDistribution() { return equipmentDistribution; }
    public void setEquipmentDistribution(Map<String, Long> equipmentDistribution) { 
        this.equipmentDistribution = equipmentDistribution; 
    }
    
    public Map<String, Long> getMonthlyRentals() { return monthlyRentals; }
    public void setMonthlyRentals(Map<String, Long> monthlyRentals) { 
        this.monthlyRentals = monthlyRentals; 
    }
}