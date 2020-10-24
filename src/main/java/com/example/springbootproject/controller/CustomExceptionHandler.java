package com.example.springbootproject.controller;

import com.example.springbootproject.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import javax.xml.ws.Response;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleException(ServiceException ex)
    {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
