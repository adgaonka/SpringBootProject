package com.example.springbootproject.persistence.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.springbootproject.model.dto.VehicleDetailsDTO;
import com.example.springbootproject.model.dto.VehicleFeatureDTO;
import com.example.springbootproject.model.dto.VehiclePriceDTO;

@Mapper
public interface VehiclesReadMapper {
	
    VehicleFeatureDTO getVehicleFeatureDTOByNameAndType(@Param("featureName") String featureName,
    		@Param("featureType") String featureType);
    
    List<Integer> getAllVehicleIds();
    
    List<VehicleDetailsDTO> getVehicleDetailsByIds(@Param("vehicleIdList") List<Integer> vehicleIdList);
    
    List<VehicleFeatureDTO> getVehicleFeatureByIds(@Param("vehicleIdList") List<Integer> vehicleIdList);
    
    List<VehiclePriceDTO> getVehiclePriceByIds(@Param("vehicleIdList") List<Integer> vehicleIdList);
    
    List<Integer> getVehiclesByModelName(@Param("modelName") String modelName);
    
    List<Integer> getVehicleByPrice(@Param("fromPrice") Double fromPrice, @Param("toPrice") Double toPrice);
    
    List<Integer> getVehicleByFeatures(@Param("exterior") String exterior, @Param("interior") String interior);
}
