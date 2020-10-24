package com.example.springbootproject.service;

import com.example.springbootproject.exception.ServiceException;
import com.example.springbootproject.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user) throws ServiceException;

    User getUserById(Long id) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    int deleteUser(Long id) throws ServiceException;

}
