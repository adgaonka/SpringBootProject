package com.example.springbootproject.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.Vehicles;
import com.example.springbootproject.persistence.read.VehiclesReadMapper;
import com.example.springbootproject.persistence.write.VehiclesWriteMapper;
import com.example.springbootproject.utils.VehicleUtils;

public class VehiclesServiceImplTest {
	@InjectMocks
    VehiclesServiceImpl vehiclesServiceImpl;
	
	@Spy
	VehicleUtils vehicleUtils;
     
    @Mock
    VehiclesWriteMapper vehiclesWriteMapper;
     
    @Mock
    VehiclesReadMapper vehiclesReadMapper;
    
    @BeforeEach
    void setUp() {
    	vehiclesServiceImpl = new VehiclesServiceImpl();
    	vehicleUtils = new VehicleUtils();
    	//vehicleUtils = Mock(VehicleUtils.class); 
    }
     
    @Test
    public void test_addVehicles_whenNoVehicles()
    {
    	VehicleServiceTestUtils vehicleServiceTestUtils = new VehicleServiceTestUtils();
    	Vehicles vehicles = vehicleServiceTestUtils.generateEmptyVehiclesObject();
    	
    	ServiceException thrown = assertThrows(
    			ServiceException.class,
    	           () -> vehiclesServiceImpl.addVehicles(vehicles),
    	           "Expected doThing() to throw, but it didn't"
    	    );

    	assertTrue(thrown.getMessage().contains("Vehicle List is empty"));
    	
    }
    
    @Test
    public void test_addVehicles_whenVehicles()
    {
    	VehicleServiceTestUtils vehicleServiceTestUtils = new VehicleServiceTestUtils();
    	
    	Vehicles vehicles = vehicleServiceTestUtils.generateVehiclesObject();
    	
    	//when(vehiclesReadMapper.getVehicleFeatureDTOByNameAndType(any(), any())).thenReturn(null);
    	try {
			vehiclesServiceImpl.addVehicles(vehicles);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    	
//    	verify(vehiclesWriteMapper, times(1)).insertVehicleDetailsList(any());
//    	verify(vehiclesWriteMapper, times(1)).insertVehiclePriceList(any());
//    	verify(vehiclesWriteMapper, times(1)).insertVehicleFeature(any());
//    	verify(vehiclesWriteMapper, times(1)).insertVehicleFeatureMapper(any());
    	
    }
    
}
