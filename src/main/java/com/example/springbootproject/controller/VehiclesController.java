package com.example.springbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.Vehicles;
import com.example.springbootproject.service.VehiclesService;

@RestController
public class VehiclesController {

	@Autowired
    VehiclesService vehiclesService;
	
	/**
     * Adds a list of vehicles using a JSON request body.
     * @param vehicleList
     * @throws ServiceException
     */
    @PostMapping("/vehicleInformation/submitVehicle")
    @ResponseStatus(value= HttpStatus.OK, reason="ID's submitted to database successfully")
    public void addUser(@RequestBody Vehicles vehiclesList) throws ServiceException {
        vehiclesService.addVehicles(vehiclesList);
    }
    
    /**
     * Returns a list of all vehicles from previous POST request
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/getVehicleInformation")
    @ResponseStatus(HttpStatus.OK)
    public Vehicles getVehicleInformation() throws ServiceException {
        return vehiclesService.getVehicleInformation();
    }
    
    /**
     * Returns vehicles with matching model name
     * @param modelName
     * @return
     * @throws ServiceException
     */
    @GetMapping("/getVehicleModelName/{modelName}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicles getVehicleModelName(@PathVariable("modelName") String modelName) throws ServiceException {
        return vehiclesService.getVehiclesByModelName(modelName);
    }
    
    /**
     * Returns vehicles with price range between fromPrice and toPrice
     * @param From - fromPrice
     * @param TO - toPrice
     * @return
     * @throws ServiceException
     */
    @GetMapping("/getVehiclePrice/{From}/{TO}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicles getVehicleByPrice(@PathVariable("From") String fromPrice, @PathVariable("TO") String toPrice) throws ServiceException {
        return vehiclesService.getVehicleByPrice(fromPrice, toPrice);
    }
    
    /**
     * Returns vehicles which contains either the exterior or the interior feature
     * @param exterior
     * @param interior
     * @return
     * @throws ServiceException
     */
    @GetMapping("/getVehicleByFeatures/{exterior}/{interior}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicles getVehicleByFeatures(@PathVariable("exterior") String exterior, @PathVariable("interior") String interior) throws ServiceException {
        return vehiclesService.getVehicleByFeatures(exterior, interior);
    }
    
    
}