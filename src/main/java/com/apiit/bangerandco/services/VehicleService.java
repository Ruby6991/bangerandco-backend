package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.VehicleDTO;
import com.apiit.bangerandco.enums.CustomerState;
import com.apiit.bangerandco.enums.UserType;
import com.apiit.bangerandco.enums.VehicleFuelType;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.models.Vehicle;
import com.apiit.bangerandco.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepo;

    @Autowired
    ModelToDTO modelToDTO;

    public ResponseEntity<Boolean> addVehicle(Vehicle newVehicle){
        newVehicle = vehicleRepo.save(newVehicle);
        if(newVehicle.getId()>0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<VehicleDTO>> getVehicleByModel(String model){
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        Iterable<Vehicle> vehicleList = vehicleRepo.findAll();
        for(Vehicle vehicle : vehicleList){
            if(vehicle.getModel().equals(model)){
                vehicleDTOS.add(modelToDTO.vehicleToDTO(vehicle));
            }
        }
        return new ResponseEntity<>(vehicleDTOS, HttpStatus.OK);
    }

    public ResponseEntity<VehicleDTO> getVehicleByLicenseNo(String licenseNo){
        Iterable<Vehicle> vehicleList = vehicleRepo.findAll();
        for(Vehicle vehicle : vehicleList){
            if(vehicle.getLicenseNo()==licenseNo){
                return new ResponseEntity<>(modelToDTO.vehicleToDTO(vehicle), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Boolean> deleteVehicle(int id){
        try{
            vehicleRepo.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    public ResponseEntity<Vehicle> updateVehicle(int id, Vehicle newVehicle){
        Optional<Vehicle> vehicleOptional = vehicleRepo.findById(id);
        if(vehicleOptional.isPresent()){
            Vehicle vehicle = vehicleOptional.get();
            vehicle.setDescription(newVehicle.getDescription());
            vehicle.setImgUrl(newVehicle.getImgUrl());
            vehicle.setMileage(newVehicle.getMileage());
            vehicle.setQuantity(newVehicle.getQuantity());
            vehicle.setRates(newVehicle.getRates());
            vehicle.setServiceDate(newVehicle.getServiceDate());
            vehicleRepo.save(vehicle);
            return new ResponseEntity<>(vehicle,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<VehicleDTO>> getVehicleList() {
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        Iterable<Vehicle> vehicleList = vehicleRepo.findAll();
        for(Vehicle vehicle : vehicleList){
            vehicleDTOList.add(modelToDTO.vehicleToDTO(vehicle));
        }
        return new ResponseEntity<>(vehicleDTOList, HttpStatus.OK);
    }
}
