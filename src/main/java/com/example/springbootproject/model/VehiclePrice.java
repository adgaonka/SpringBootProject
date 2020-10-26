package com.example.springbootproject.model;

public class VehiclePrice {
	
	private String MSRP;
	private String Savings;
	private String finalPrice;
	
	public String getMSRP() {
		return MSRP;
	}
	public void setMSRP(String mSRP) {
		MSRP = mSRP;
	}
	public String getSavings() {
		return Savings;
	}
	public void setSavings(String savings) {
		Savings = savings;
	}
	public String getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
}
