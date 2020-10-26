package com.example.springbootproject.model;

import java.util.List;

public class VehicleDetails {
	private String make;
	private String model;
	private String modelYear;
	private String bodyStyle;
	private String engine;
	private String drivetype;
	private String color;
	private String mpg;
	private VehicleFeature vehicleFeature;
	private List<VehiclePrice> vehiclePrice;
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getBodyStyle() {
		return bodyStyle;
	}
	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getDrivetype() {
		return drivetype;
	}
	public void setDrivetype(String drivetype) {
		this.drivetype = drivetype;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMpg() {
		return mpg;
	}
	public void setMpg(String mpg) {
		this.mpg = mpg;
	}
	public VehicleFeature getVehicleFeature() {
		return vehicleFeature;
	}
	public void setVehicleFeature(VehicleFeature vehicleFeature) {
		this.vehicleFeature = vehicleFeature;
	}
	public List<VehiclePrice> getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(List<VehiclePrice> vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}
	
}
