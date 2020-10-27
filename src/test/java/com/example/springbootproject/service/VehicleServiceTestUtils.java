package com.example.springbootproject.service;

import java.util.ArrayList;
import java.util.List;

import com.example.springbootproject.model.Vehicle;
import com.example.springbootproject.model.VehicleDetails;
import com.example.springbootproject.model.VehicleFeature;
import com.example.springbootproject.model.VehicleInfo;
import com.example.springbootproject.model.VehiclePrice;
import com.example.springbootproject.model.Vehicles;

public class VehicleServiceTestUtils {
	
	public Vehicles generateEmptyVehiclesObject() {
    	Vehicles vehicles = new Vehicles();
    	return vehicles;
    }
    
    public Vehicles generateVehiclesObject() {
    	Vehicles vehicles = new Vehicles();
    	Vehicle vehicle = new Vehicle();
    	List<VehicleInfo> vehicleInfoList = new ArrayList<>();
    	VehicleInfo vehicleInfo = new VehicleInfo();
    	VehicleDetails vehicleDetails = new VehicleDetails();
    	vehicleDetails.setMake("Ford");
    	vehicleDetails.setModel("ecoSport");
    	vehicleDetails.setModelYear("2020");
    	vehicleDetails.setBodyStyle("4d Sport Utility");
    	vehicleDetails.setEngine("1.0L EcoBoost");
    	vehicleDetails.setDrivetype("FWD");
    	vehicleDetails.setColor("Shadow Black");
    	vehicleDetails.setMpg("27");
    	VehicleFeature vehicleFeature = new VehicleFeature();
    	List<String> exteriorFeature = new ArrayList<>();
    	exteriorFeature.add("Acoustic-Laminate Windshld");
    	exteriorFeature.add("Active Grille Shitters");
    	vehicleFeature.setExterior(exteriorFeature);
    	List<String> interiorFeature = new ArrayList<>();
    	interiorFeature.add("Illuminated Entry System");
    	interiorFeature.add("Powerpoints - 12V");
    	vehicleFeature.setInterior(interiorFeature);
    	vehicleDetails.setVehicleFeature(vehicleFeature);
    	List<VehiclePrice> vehiclePriceList = new ArrayList<>();
    	VehiclePrice vehiclePrice = new VehiclePrice();
    	vehiclePrice.setMSRP("$25,000.00");
    	vehiclePrice.setSavings("$5000");
    	vehiclePrice.setFinalPrice("$20,000.00");
    	vehiclePriceList.add(vehiclePrice);
    	vehicleDetails.setVehiclePrice(vehiclePriceList);
    	vehicleInfo.setVehicleDetails(vehicleDetails);
    	vehicleInfo.setVehicleId("101");
    	vehicleInfoList.add(vehicleInfo);
    	vehicle.setVehicle(vehicleInfoList);
    	vehicles.setVehicles(vehicle);
    	return vehicles;
    }
}
