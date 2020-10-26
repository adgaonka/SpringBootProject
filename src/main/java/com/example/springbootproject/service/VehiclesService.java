package com.example.springbootproject.service;

import java.util.List;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.Vehicles;
import com.example.springbootproject.model.dto.VehicleDetailsDTO;
import com.example.springbootproject.model.dto.VehicleFeatureDTO;
import com.example.springbootproject.model.dto.VehiclePriceDTO;

public interface VehiclesService {

	void addVehicles(Vehicles vehiclesList) throws ServiceException;
	
	void insertVehicleDetails(List<VehicleDetailsDTO> vehicleDetailsDTOList) throws ServiceException;
	
	void insertVehiclePrice(List<VehiclePriceDTO> vehiclePriceDTOList) throws ServiceException;
	
	void insertVehicleFeatures(List<VehicleFeatureDTO> vehicleFeatureDTOList) throws ServiceException;
	
	Vehicles getVehicleInformation() throws ServiceException;
	
	Vehicles getVehiclesByModelName(String modelName) throws ServiceException;
	
	Vehicles getVehicleByPrice(String fromPrice, String toPrice) throws ServiceException;
	
	Vehicles getVehicleByFeatures(String exterior, String interior) throws ServiceException;
}
