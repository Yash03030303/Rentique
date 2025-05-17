package com.capgemini.equipment_rental.dto;

public class LongestRental {
	
	private String equipmentName;
    private Long durationDays;
    
    public LongestRental(String equipmentName, Long durationDays) {
        this.equipmentName = equipmentName;
        this.durationDays = durationDays;
    }

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public Long getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(Long durationDays) {
		this.durationDays = durationDays;
	}
    
    
}
