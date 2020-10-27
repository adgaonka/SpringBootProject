# Simple Vehicle Search Application

Create a simple vehicle search APIs using RESTful pattern in Java using Spring Boot. Database used in the H2 in memory database for simplification in the testing purposes and for peristence MyBatis is used

## Installation

Using the pom.xml file resolve the dependency

```bash
mvn clean install package
```

## APIS Implemented

1. POST /vehicleInfomation/submitVehicle - This API will be used to persist the data in the H2 database
2. GET /getVehicleInformation - This API will pull all the stored vehicle information
3. GET/getVehicleModelName/{modelName} - This API will pull all the vehicles with the matching model name
4. GET/getVehiclePrice/{From}/{TO} - This API will pull all the vehicles with price between from and to range
5. GET/getVehicleByFeatures/{exterior}/{interior} - This API will pull all the vehicles matching the interior and exterior features

## Database Tables
It exists in the schema.sql file in the project and the coniguration is done using SQLSessionFactory. There are 4 tables created i.e 
Vehicle_Details - contains all the basic car information 
vehicle_feature - contains the features of the car 
vehicle_feature_mapper - mapper file between the car and features since there is a many to many relationship
vehicle_price - to store the vehicle price information

## Security
APIs are secured using Basic Authentication from the Spring Security framework. APIs will be needing the appropriate credentials to hit the endpoint

## Testing
Unit tests are implementes using Junits and Mockito

## Containerization
Docker file is added which has commands to set up a docker images

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
