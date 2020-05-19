package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.dtos.VehicleDTO;
import com.apiit.bangerandco.models.Vehicle;
import com.apiit.bangerandco.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PutMapping("/AddVehicle")
    public ResponseEntity<Boolean> AddVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.addVehicle(vehicle);
    }

    @PostMapping("/GetVehicleModel")
    public ResponseEntity<List<VehicleDTO>> GetVehicleByModel(@RequestBody Vehicle vehicle){
        return vehicleService.getVehicleByModel(vehicle.getModel());
    }

    @PostMapping("/GetVehicleLicense")
    public ResponseEntity<VehicleDTO> getVehicleByLicense(@RequestBody Vehicle vehicle){
        return vehicleService.getVehicleByLicenseNo(vehicle.getLicenseNo());
    }

    @PostMapping("/GetVehicleList")
    public ResponseEntity<List<VehicleDTO>> getVehicleList(){
        return vehicleService.getVehicleList();
    }

    @DeleteMapping("/DeleteVehicle/{id}")
    public ResponseEntity<Boolean> DeleteVehicle(@PathVariable int id){
        return vehicleService.deleteVehicle(id);
    }

    @PutMapping("/UpdateVehicle/{id}")
    public ResponseEntity<Vehicle> UpdateVehicle(@PathVariable int id, @RequestBody Vehicle vehicle){
        return vehicleService.updateVehicle(id,vehicle);
    }

}
