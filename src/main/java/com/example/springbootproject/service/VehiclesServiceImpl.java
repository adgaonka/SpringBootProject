package com.example.springbootproject.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.VehicleDetails;
import com.example.springbootproject.model.VehicleFeature;
import com.example.springbootproject.model.VehicleInfo;
import com.example.springbootproject.model.VehiclePrice;
import com.example.springbootproject.model.Vehicles;
import com.example.springbootproject.model.dto.VehicleDetailsDTO;
import com.example.springbootproject.model.dto.VehicleFeatureDTO;
import com.example.springbootproject.model.dto.VehiclePriceDTO;
import com.example.springbootproject.persistence.read.VehiclesReadMapper;
import com.example.springbootproject.persistence.write.VehiclesWriteMapper;
import com.example.springbootproject.utils.VehicleUtils;

@Service
public class VehiclesServiceImpl implements VehiclesService {
	
	@Autowired
	VehicleUtils vehicleUtils;
	
	@Autowired
	VehiclesReadMapper vehiclesReadMapper;
	
	@Autowired
	VehiclesWriteMapper vehiclesWriteMapper;
	
	@Override
	@Transactional
	public void addVehicles(Vehicles vehiclesList) throws ServiceException {
		try {
			if(vehiclesList!=null && vehiclesList.getVehicles()!=null 
					&& CollectionUtils.isNotEmpty(vehiclesList.getVehicles().getVehicle())) {
				
				/*
				 * Create a DTO objects for inserting into the database tables
				 */
				List<VehicleDetailsDTO> vehicleDetailsDTOList = new ArrayList<>();
				List<VehicleFeatureDTO> vehicleFeatureDTOList = new ArrayList<>();
				List<VehiclePriceDTO> vehiclePriceDTOList = new ArrayList<>();
				
				/*
				 * Start processing the post payload of vehicles list
				 */
				List<VehicleInfo> vehicleInfoList = vehiclesList.getVehicles().getVehicle();
				vehicleInfoList.forEach(vehicleInfo -> {
					if(vehicleInfo!=null) {
						String vehicleId = vehicleInfo.getVehicleId();
						if(vehicleUtils.isStringEmpty(vehicleId)){
							return;
						}
						if(vehicleInfo.getVehicleDetails()==null) {
							return;
						}
						VehicleDetails vehicleDetails = vehicleInfo.getVehicleDetails();
						if(!vehicleUtils.validateForInsertVehicleDetails(vehicleDetails)) {
							return;
						}
						List<VehiclePrice> vehiclePriceList = vehicleDetails.getVehiclePrice();
						if(CollectionUtils.isEmpty(vehiclePriceList)) {
							return;
						}
						if(!vehicleUtils.validateForInsertVehiclePrice(vehiclePriceList)) {
							return;
						}
						
						/*
						 * Once all the validations are completed start processing and saving the record
						 */
						VehicleDetailsDTO vehicleDetailsDTO = vehicleUtils.mapVehicleDetailsPOJOToDTO(vehicleId, vehicleDetails);
						vehicleDetailsDTOList.add(vehicleDetailsDTO);
						
						VehicleFeature vehicleFeature = vehicleDetails.getVehicleFeature();
						List<VehicleFeatureDTO> currentVehlFeatDTOList = vehicleUtils.mapVehicleFeaturePOJOToDTO(vehicleId, vehicleFeature);
						vehicleFeatureDTOList.addAll(currentVehlFeatDTOList);
						
						VehiclePriceDTO vehiclePriceDTO = vehicleUtils.mapVehiclePricePOJOToDTO(vehicleId, vehiclePriceList.get(0));
						vehiclePriceDTOList.add(vehiclePriceDTO);
						
					}
				});
				
				/*
				 * Insert the records inn the respective tables from the list
				 */
				insertVehicleDetails(vehicleDetailsDTOList);
				insertVehiclePrice(vehiclePriceDTOList);
				insertVehicleFeatures(vehicleFeatureDTOList);
				
			}else {
				throw new ServiceException("Vehicle List is empty");
			}
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public void insertVehicleDetails(List<VehicleDetailsDTO> vehicleDetailsDTOList) throws ServiceException {
		try {
			if(CollectionUtils.isNotEmpty(vehicleDetailsDTOList)) {
				vehiclesWriteMapper.insertVehicleDetailsList(vehicleDetailsDTOList);
			}
		} catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public void insertVehiclePrice(List<VehiclePriceDTO> vehiclePriceDTOList) throws ServiceException {
		try {
			if(CollectionUtils.isNotEmpty(vehiclePriceDTOList)) {
				vehiclesWriteMapper.insertVehiclePriceList(vehiclePriceDTOList);
			}
		} catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public void insertVehicleFeatures(List<VehicleFeatureDTO> vehicleFeatureDTOList) throws ServiceException {
		try {
			if(CollectionUtils.isNotEmpty(vehicleFeatureDTOList)) {
				vehicleFeatureDTOList.forEach(vehicleFeature -> {
					/*
					 * Check if the feature exist in the feature table
					 */
					VehicleFeatureDTO vehicleFeatureDTO = vehiclesReadMapper.
							getVehicleFeatureDTOByNameAndType(vehicleFeature.getFeatureName(), vehicleFeature.getFeatureType());
					
					if(vehicleFeatureDTO == null) {
						vehiclesWriteMapper.insertVehicleFeature(vehicleFeature);
					}else {
						vehicleFeature.setFeatureId(vehicleFeatureDTO.getFeatureId());
					}
					vehiclesWriteMapper.insertVehicleFeatureMapper(vehicleFeature);
				});
			}
		} catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public Vehicles getVehicleInformation() throws ServiceException {
		Vehicles vehicles = new Vehicles();
		try{
			List<Integer> vehicleIdList = vehiclesReadMapper.getAllVehicleIds();
			if(CollectionUtils.isNotEmpty(vehicleIdList)) {
				List<VehicleDetailsDTO> vehicleDetailsDTOList = vehiclesReadMapper.getVehicleDetailsByIds(vehicleIdList);
				List<VehicleFeatureDTO> vehicleFeatureDTOList = vehiclesReadMapper.getVehicleFeatureByIds(vehicleIdList);
				List<VehiclePriceDTO> vehiclePriceDTOList = vehiclesReadMapper.getVehiclePriceByIds(vehicleIdList);
				
				vehicles = vehicleUtils.createVehiclePayloadInfo(vehicleDetailsDTOList, vehicleFeatureDTOList, vehiclePriceDTOList, vehicleIdList);
			}
            return vehicles;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public Vehicles getVehiclesByModelName(String modelName) throws ServiceException {
		Vehicles vehicles = new Vehicles();
		try{
			if(vehicleUtils.isStringEmpty(modelName)) {
				throw new ServiceException("Model Name is empty");
			}
			List<Integer> vehicleIdList = vehiclesReadMapper.getVehiclesByModelName(modelName);
			if(CollectionUtils.isNotEmpty(vehicleIdList)) {
				List<VehicleDetailsDTO> vehicleDetailsDTOList = vehiclesReadMapper.getVehicleDetailsByIds(vehicleIdList);
				List<VehicleFeatureDTO> vehicleFeatureDTOList = vehiclesReadMapper.getVehicleFeatureByIds(vehicleIdList);
				List<VehiclePriceDTO> vehiclePriceDTOList = vehiclesReadMapper.getVehiclePriceByIds(vehicleIdList);
				
				vehicles = vehicleUtils.createVehiclePayloadInfo(vehicleDetailsDTOList, vehicleFeatureDTOList, vehiclePriceDTOList, vehicleIdList);
			}else {
				throw new ServiceException("No vehicle exist with the model name " + modelName);
			}
            return vehicles;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public Vehicles getVehicleByPrice(String fromPrice, String toPrice) throws ServiceException {
		Vehicles vehicles = new Vehicles();
		try{
			if(vehicleUtils.isStringEmpty(fromPrice) || vehicleUtils.isStringEmpty(toPrice)) {
				throw new ServiceException("Either/Both of fromPrice or ToPrice is empty");
			}
			Double fromPriceDouble = Double.valueOf(NumberFormat.getCurrencyInstance().parse("$" + fromPrice).toString());
			Double toPriceDouble = Double.valueOf(NumberFormat.getCurrencyInstance().parse("$" + toPrice).toString());
			List<Integer> vehicleIdList = vehiclesReadMapper.getVehicleByPrice(fromPriceDouble, toPriceDouble);
			if(CollectionUtils.isNotEmpty(vehicleIdList)) {
				List<VehicleDetailsDTO> vehicleDetailsDTOList = vehiclesReadMapper.getVehicleDetailsByIds(vehicleIdList);
				List<VehicleFeatureDTO> vehicleFeatureDTOList = vehiclesReadMapper.getVehicleFeatureByIds(vehicleIdList);
				List<VehiclePriceDTO> vehiclePriceDTOList = vehiclesReadMapper.getVehiclePriceByIds(vehicleIdList);
				
				vehicles = vehicleUtils.createVehiclePayloadInfo(vehicleDetailsDTOList, vehicleFeatureDTOList, vehiclePriceDTOList, vehicleIdList);
			}else {
				throw new ServiceException("No vehicle exist between the price range " + fromPrice + " - " + toPrice);
			}
            return vehicles;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
	
	@Override
	public Vehicles getVehicleByFeatures(String exterior, String interior) throws ServiceException {
		Vehicles vehicles = new Vehicles();
		try{
			if(vehicleUtils.isStringEmpty(exterior) || vehicleUtils.isStringEmpty(interior)) {
				throw new ServiceException("Either/Both of interior or exterior is empty");
			}
			if(exterior.length()<3 || interior.length()<3) {
				throw new ServiceException("Either/Both of interior or exterior length is less than 3 characters");
			}
			List<Integer> vehicleIdList = vehiclesReadMapper.getVehicleByFeatures(exterior, interior);
			if(CollectionUtils.isNotEmpty(vehicleIdList)) {
				List<VehicleDetailsDTO> vehicleDetailsDTOList = vehiclesReadMapper.getVehicleDetailsByIds(vehicleIdList);
				List<VehicleFeatureDTO> vehicleFeatureDTOList = vehiclesReadMapper.getVehicleFeatureByIds(vehicleIdList);
				List<VehiclePriceDTO> vehiclePriceDTOList = vehiclesReadMapper.getVehiclePriceByIds(vehicleIdList);
				
				vehicles = vehicleUtils.createVehiclePayloadInfo(vehicleDetailsDTOList, vehicleFeatureDTOList, vehiclePriceDTOList, vehicleIdList);
			}else {
				throw new ServiceException("No vehicle exist with the exterior " + exterior + " or interior " + interior);
			}
            return vehicles;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
	}
}
