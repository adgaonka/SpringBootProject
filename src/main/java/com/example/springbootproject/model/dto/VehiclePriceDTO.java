package com.example.springbootproject.model.dto;

public class VehiclePriceDTO {
	private Integer vehicleId;
	private Double MSRP;
	private Double Savings;
	private Double finalPrice;
	
	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Double getMSRP() {
		return MSRP;
	}
	public void setMSRP(Double mSRP) {
		MSRP = mSRP;
	}
	public Double getSavings() {
		return Savings;
	}
	public void setSavings(Double savings) {
		Savings = savings;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
}
