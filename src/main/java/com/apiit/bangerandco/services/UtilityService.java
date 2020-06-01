package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.UtilityDTO;
import com.apiit.bangerandco.models.Utility;
import com.apiit.bangerandco.repositories.UtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityService {

    @Autowired
    UtilityRepository utilityRepo;

    @Autowired
    ModelToDTO modelToDTO;

    public ResponseEntity<Boolean> addUtility(Utility newUtility){
        newUtility = utilityRepo.save(newUtility);
        if(newUtility.getId()>0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<UtilityDTO> getUtilityByTitle(String title){
        UtilityDTO utilityDTO = new UtilityDTO();
        Iterable<Utility> utilityList = utilityRepo.findAll();
        for(Utility utility : utilityList){
            if(utility.getUtilityName().equals(title)){
                utilityDTO=modelToDTO.utilityToDTO(utility);
            }
        }
        return new ResponseEntity<>(utilityDTO, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> deleteUtility(int id){
        try{
            utilityRepo.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    public ResponseEntity<Utility> updateUtility(int id, Utility newUtility){
        Optional<Utility> utilityOptional = utilityRepo.findById(id);
        if(utilityOptional.isPresent()){
            Utility utility = utilityOptional.get();
            utility.setQuantity(newUtility.getQuantity());
            utility.setUtilityRate(newUtility.getUtilityRate());
            utility.setUtilityImg(newUtility.getUtilityImg());
            utilityRepo.save(utility);
            return new ResponseEntity<>(utility,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<UtilityDTO>> getUtilityList() {
        List<UtilityDTO> utilityDTOList = new ArrayList<>();
        Iterable<Utility> utilityList = utilityRepo.findAll();
        for(Utility utility : utilityList){
            if(utility.isUtilityAvailability()){
                utilityDTOList.add(modelToDTO.utilityToDTO(utility));
            }
        }
        return new ResponseEntity<>(utilityDTOList, HttpStatus.OK);
    }

}
