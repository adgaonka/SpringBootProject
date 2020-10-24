package com.example.springbootproject.service;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.User;
import com.example.springbootproject.sql.UserJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootproject.persistence.read.VehicleDetailsReadMapper;

import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserJdbcRepository repository;
    
    @Autowired
    private VehicleDetailsReadMapper vehicleDetailsReadMapper;

    @Override
    public int addUser(User user) throws ServiceException {
        try {
            User userValidated = repository.findByFullName(user);
            if(userValidated != null){
                throw new ServiceException("Same first name/last name combination is already stored in the system");
            }
            return repository.insertUser(user);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        try{
        	return null;
        	//return vehicleDetailsReadMapper.getUserInfo(id);
            //return repository.findUserById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try{
            List<User> userList = repository.findAllUsers();
            userList.sort(Comparator.comparing(User::getLastName));
            return userList;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int deleteUser(Long id) throws ServiceException {
        try{
            return repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
