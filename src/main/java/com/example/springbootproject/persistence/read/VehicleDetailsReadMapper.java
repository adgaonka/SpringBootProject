package com.example.springbootproject.persistence.read;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.springbootproject.model.User;

public interface VehicleDetailsReadMapper {

	User getUserInfo();
	
}
