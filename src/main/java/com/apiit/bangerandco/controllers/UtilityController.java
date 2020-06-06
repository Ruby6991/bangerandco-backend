package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.dtos.UtilityDTO;
import com.apiit.bangerandco.dtos.VehicleDTO;
import com.apiit.bangerandco.models.Utility;
import com.apiit.bangerandco.services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UtilityController {

    @Autowired
    UtilityService utilityService;

    @PutMapping("/AddUtility")
    public ResponseEntity<Boolean> AddUtility(@RequestBody Utility utility){
        return utilityService.addUtility(utility);
    }

    @GetMapping("/GetUtility/{id}")
    public ResponseEntity<UtilityDTO> getUtilityByID(@PathVariable int id){
        return utilityService.getUtilityById(id);
    }

    @PostMapping("/GetUtilityTitle")
    public ResponseEntity<UtilityDTO> GetUtilityByTitle(@RequestBody Utility utility){
        return utilityService.getUtilityByTitle(utility.getUtilityName());
    }

    @PostMapping("/GetUtilityList")
    public ResponseEntity<List<UtilityDTO>> getUtilityList(){
        return utilityService.getUtilityList();
    }

    @PostMapping("/GetAllUtilities")
    public ResponseEntity<List<UtilityDTO>> getAllUtilities(){
        return utilityService.getAllUtilities();
    }

    @DeleteMapping("/DeleteUtility/{id}")
    public ResponseEntity<Boolean> DeleteUtility(@PathVariable int id){
        return utilityService.deleteUtility(id);
    }

    @PutMapping("/UpdateUtility/{id}")
    public ResponseEntity<Utility> UpdateUtility(@PathVariable int id, @RequestBody Utility utility){
        return utilityService.updateUtility(id,utility);
    }

}
