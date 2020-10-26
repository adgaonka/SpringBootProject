package com.example.springbootproject.persistence.write;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.springbootproject.model.dto.VehicleDetailsDTO;
import com.example.springbootproject.model.dto.VehicleFeatureDTO;
import com.example.springbootproject.model.dto.VehiclePriceDTO;

@Mapper
public interface VehiclesWriteMapper {

	void insertVehicleDetailsList(@Param("vehicleDetailsDTOList") List<VehicleDetailsDTO> vehicleDetailsDTOList);
	
	void insertVehiclePriceList(@Param("vehiclePriceDTOList") List<VehiclePriceDTO> vehiclePriceDTOList);
	
	int insertVehicleFeature(VehicleFeatureDTO vehicleFeatureDTO);
	
	void insertVehicleFeatureMapper(VehicleFeatureDTO vehicleFeatureDTO);
}
