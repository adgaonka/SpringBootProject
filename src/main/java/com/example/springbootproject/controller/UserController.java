package com.example.springbootproject.controller;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.User;
import com.example.springbootproject.persistence.read.VehicleDetailsReadMapper;
import com.example.springbootproject.service.UserService;
import com.example.springbootproject.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    private VehicleDetailsReadMapper vehicleDetailsReadMapper;

    /**
     * Adds a User to the list of stored users using a JSON request body.
     * The request should be rejected if the same first name/last name combination is already stored in the system.
     * @param newUser
     * @throws ServiceException
     */
    @PostMapping("/addUser")
    @ResponseStatus(value= HttpStatus.CREATED, reason="User created")
    public void addUser(@RequestBody User newUser) throws ServiceException {
    	userService.addUser(newUser);
    }

    /**
     * Returns a single User when requested by ID as JSON
     * @param id
     * @return
     * @throws ServiceException
     */
    @GetMapping("/getUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("id") Long id) throws ServiceException {
        //return null;
    	return vehicleDetailsReadMapper.getUserInfo();
    	//return userService.getUserById(id);
    }

    /**
     * Returns a list of all Users as JSON, ordered alphabetically by last name
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/getAllUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUser() throws ServiceException {
        return userService.getAllUsers();
    }

    /**
     * Deletes a User when requested by ID
     * @param id
     * @throws ServiceException
     */
    @DeleteMapping("/deleteUser/{id}")
    @ResponseStatus(value= HttpStatus.NO_CONTENT, reason="User deleted")
    public void deleteUser(@PathVariable Long id) throws ServiceException {
    	userService.deleteUser(id);
    }

}

