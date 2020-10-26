package com.example.springbootproject.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.Vehicle;
import com.example.springbootproject.model.VehicleDetails;
import com.example.springbootproject.model.VehicleFeature;
import com.example.springbootproject.model.VehicleInfo;
import com.example.springbootproject.model.VehiclePrice;
import com.example.springbootproject.model.Vehicles;
import com.example.springbootproject.model.dto.VehicleDetailsDTO;
import com.example.springbootproject.model.dto.VehicleFeatureDTO;
import com.example.springbootproject.model.dto.VehiclePriceDTO;

@Component
public class VehicleUtils {

	private static final String EXTERIOR_FEATURE_TYPE = "Exterior";
	private static final String INTERIOR_FEATURE_TYPE = "Interior";
	
	public boolean isStringEmpty(String str) {
		if(str == null || str.trim().isEmpty()){
			return true;
		}
		return false;
	}
	
	public boolean validateForInsertVehicleDetails(VehicleDetails vehicleDetails) {
		/*
		 * Create a stream of all the property to do a null and empty check on string
		 */
		if (Stream.of(vehicleDetails.getMake(), vehicleDetails.getModel(),
				vehicleDetails.getModelYear(), vehicleDetails.getBodyStyle(), 
				vehicleDetails.getEngine(), vehicleDetails.getDrivetype(), vehicleDetails.getColor(),
				vehicleDetails.getMpg())
				.allMatch(x -> !isStringEmpty(x))) {
			return true;
		}
		return false;
	}
	
	public boolean validateForInsertVehiclePrice(List<VehiclePrice> vehiclePriceList) {
		/*
		 * Create a stream of all the property to do a null and empty check on string
		 */
		VehiclePrice vehiclePrice = vehiclePriceList.get(0);
		if (Stream.of(vehiclePrice.getMSRP(), vehiclePrice.getSavings(), vehiclePrice.getFinalPrice())
				.allMatch(x -> !isStringEmpty(x))) {
			return true;
		}
		return false;
	}
	
	public VehicleDetailsDTO mapVehicleDetailsPOJOToDTO(String vehicleId, VehicleDetails vehicleDetails) {
		VehicleDetailsDTO vehicleDetailsDTO = new VehicleDetailsDTO();
		vehicleDetailsDTO.setVehicleId(Integer.parseInt(vehicleId));
		vehicleDetailsDTO.setMake(vehicleDetails.getMake());
		vehicleDetailsDTO.setModel(vehicleDetails.getModel());
		vehicleDetailsDTO.setModelYear(Integer.parseInt(vehicleDetails.getModelYear()));
		vehicleDetailsDTO.setBodyStyle(vehicleDetails.getBodyStyle());
		vehicleDetailsDTO.setEngine(vehicleDetails.getEngine());
		vehicleDetailsDTO.setDriveType(vehicleDetails.getDrivetype());
		vehicleDetailsDTO.setColor(vehicleDetails.getColor());
		vehicleDetailsDTO.setMPG(Double.valueOf(vehicleDetails.getMpg()));
		return vehicleDetailsDTO;
	}
	
	public List<VehicleFeatureDTO> mapVehicleFeaturePOJOToDTO(String vehicleId, VehicleFeature vehicleFeature) {
		if(vehicleFeature==null) {
			return null;
		}
		
		List<VehicleFeatureDTO> vehicleFeatureDTOList = new ArrayList<>();
		
		List<String> exteriorFeatures = vehicleFeature.getExterior();
		if(CollectionUtils.isNotEmpty(exteriorFeatures)) {
			exteriorFeatures.forEach(exteriorFeature -> {
				VehicleFeatureDTO vehicleFeatureDTO = new VehicleFeatureDTO();
				vehicleFeatureDTO.setVehicleId(Integer.parseInt(vehicleId));
				vehicleFeatureDTO.setFeatureName(exteriorFeature);
				vehicleFeatureDTO.setFeatureType(EXTERIOR_FEATURE_TYPE);
				vehicleFeatureDTOList.add(vehicleFeatureDTO);
			});
		}
		
		List<String> interiorFeatures = vehicleFeature.getInterior();
		if(CollectionUtils.isNotEmpty(interiorFeatures)) {
			interiorFeatures.forEach(interiorFeature -> {
				VehicleFeatureDTO vehicleFeatureDTO = new VehicleFeatureDTO();
				vehicleFeatureDTO.setVehicleId(Integer.parseInt(vehicleId));
				vehicleFeatureDTO.setFeatureName(interiorFeature);
				vehicleFeatureDTO.setFeatureType(INTERIOR_FEATURE_TYPE);
				vehicleFeatureDTOList.add(vehicleFeatureDTO);
			});
		}
		return vehicleFeatureDTOList;
	}
	
	public VehiclePriceDTO mapVehiclePricePOJOToDTO(String vehicleId, VehiclePrice vehiclePrice) {
		VehiclePriceDTO vehiclePriceDTO = new VehiclePriceDTO();
		vehiclePriceDTO.setVehicleId(Integer.parseInt(vehicleId));
		try {
			vehiclePriceDTO.setMSRP(Double.valueOf(NumberFormat.getCurrencyInstance().parse(vehiclePrice.getMSRP()).toString()));
			vehiclePriceDTO.setSavings(Double.valueOf(NumberFormat.getCurrencyInstance().parse(vehiclePrice.getSavings()).toString()));
			vehiclePriceDTO.setFinalPrice(Double.valueOf(NumberFormat.getCurrencyInstance().parse(vehiclePrice.getFinalPrice()).toString()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return vehiclePriceDTO;
	}
	
	public Vehicles createVehiclePayloadInfo(List<VehicleDetailsDTO> vehicleDetailsDTOList,
			List<VehicleFeatureDTO> vehicleFeatureDTOList, 
			List<VehiclePriceDTO> vehiclePriceDTOList,
			List<Integer> vehicleIdList) {
		Vehicles vehicles = new Vehicles();
		
		if(CollectionUtils.isNotEmpty(vehicleIdList)) {
			Vehicle vehicle = new Vehicle();
			List<VehicleInfo> vehicleInfoList = new ArrayList<>();
			vehicleIdList.forEach(vehicleId -> {
				VehicleInfo vehicleInfo = new VehicleInfo();
				vehicleInfo.setVehicleId(vehicleId.toString());
				
				VehicleDetails vehicleDetails = mapVehicleDetailDTOToPOJO(vehicleId, vehicleDetailsDTOList);
				vehicleDetails.setVehicleFeature(mapVehicleFeatureDTOToPOJO(vehicleId, vehicleFeatureDTOList));
				vehicleDetails.setVehiclePrice(mapVehiclePriceDTOToPOJO(vehicleId, vehiclePriceDTOList));
				vehicleInfo.setVehicleDetails(vehicleDetails);
				
				vehicleInfoList.add(vehicleInfo);
			});
			
			vehicle.setVehicle(vehicleInfoList);
			vehicles.setVehicles(vehicle);
		}
		
		return vehicles;
	}
	
	public VehicleDetails mapVehicleDetailDTOToPOJO(Integer vehicleId, List<VehicleDetailsDTO> vehicleDetailsDTOList) {
		VehicleDetails vehicleDetails = new VehicleDetails();
		if(CollectionUtils.isNotEmpty(vehicleDetailsDTOList)) {
			VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsDTOList.stream()
					.filter(x -> vehicleId==x.getVehicleId())
					.findAny()
					.orElse(null);
			if(vehicleDetailsDTO!=null) {
				vehicleDetails.setMake(vehicleDetailsDTO.getMake());
				vehicleDetails.setModel(vehicleDetailsDTO.getModel());
				vehicleDetails.setModelYear(String.valueOf(vehicleDetailsDTO.getModelYear()));
				vehicleDetails.setBodyStyle(vehicleDetailsDTO.getBodyStyle());
				vehicleDetails.setEngine(vehicleDetailsDTO.getEngine());
				vehicleDetails.setDrivetype(vehicleDetailsDTO.getDriveType());
				vehicleDetails.setColor(vehicleDetailsDTO.getColor());
				vehicleDetails.setMpg(String.valueOf(vehicleDetailsDTO.getMPG()));
			}
		}
		return vehicleDetails;
	}
	
	public VehicleFeature mapVehicleFeatureDTOToPOJO(Integer vehicleId, List<VehicleFeatureDTO> vehicleFeatureDTOList) {
		if(CollectionUtils.isNotEmpty(vehicleFeatureDTOList)) {
			List<VehicleFeatureDTO> filteredVehicleFeatureList = vehicleFeatureDTOList.stream()
					.filter(vehicleFeatureDTO -> vehicleId==vehicleFeatureDTO.getVehicleId())
					.collect(Collectors.toList());
			
			if(CollectionUtils.isNotEmpty(filteredVehicleFeatureList)) {
				VehicleFeature vehicleFeature = new VehicleFeature();
				List<String> exteriorFeatures = filteredVehicleFeatureList.stream()
						.filter(filteredFeature -> EXTERIOR_FEATURE_TYPE.equalsIgnoreCase(filteredFeature.getFeatureType()))
						.map(filteredFeature -> filteredFeature.getFeatureName())
						.collect(Collectors.toList());
				vehicleFeature.setExterior(exteriorFeatures);
				
				List<String> interiorFeatures = filteredVehicleFeatureList.stream()
						.filter(filteredFeature -> INTERIOR_FEATURE_TYPE.equalsIgnoreCase(filteredFeature.getFeatureType()))
						.map(filteredFeature -> filteredFeature.getFeatureName())
						.collect(Collectors.toList());
				vehicleFeature.setInterior(interiorFeatures);
				return vehicleFeature;
			}
		}
		return null;
	}
	
	public List<VehiclePrice> mapVehiclePriceDTOToPOJO(Integer vehicleId, List<VehiclePriceDTO> vehiclePriceDTOList){
		if(CollectionUtils.isNotEmpty(vehiclePriceDTOList)) {
			List<VehiclePriceDTO> filteredVehiclePriceList = vehiclePriceDTOList.stream()
					.filter(vehiclePriceDTO -> vehicleId==vehiclePriceDTO.getVehicleId())
					.collect(Collectors.toList());
			
			if(CollectionUtils.isNotEmpty(filteredVehiclePriceList)) {
				List<VehiclePrice> vehiclePriceList = new ArrayList<>();
				filteredVehiclePriceList.forEach(filteredVehicle -> {
					VehiclePrice vehiclePrice = new VehiclePrice();
					vehiclePrice.setMSRP("$" + String.valueOf(filteredVehicle.getMSRP()));
					vehiclePrice.setSavings("$" + String.valueOf(filteredVehicle.getSavings()));
					vehiclePrice.setFinalPrice("$" + String.valueOf(filteredVehicle.getFinalPrice()));
					vehiclePriceList.add(vehiclePrice);
				});
				return vehiclePriceList;
			}
		}
		return null;
	}
}
