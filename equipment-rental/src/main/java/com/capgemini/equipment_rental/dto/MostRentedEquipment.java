package com.capgemini.equipment_rental.dto;

public class MostRentedEquipment {
	
	private String equipmentName;
    private Long rentalCount;
    
    public MostRentedEquipment(String equipmentName, Long rentalCount) {
        this.equipmentName = equipmentName;
        this.rentalCount = rentalCount;
    }

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public Long getRentalCount() {
		return rentalCount;
	}

	public void setRentalCount(Long rentalCount) {
		this.rentalCount = rentalCount;
	}
     
}
