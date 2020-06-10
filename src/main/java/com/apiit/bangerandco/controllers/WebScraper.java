package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.models.CompetitorVehicle;
import com.apiit.bangerandco.services.WebScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class WebScraper {

    @Autowired
    WebScraperService webScraperService;

    @GetMapping("/getCompetitorInfo")
    public ResponseEntity<List<CompetitorVehicle>> getCompetitorInfo(){
        return webScraperService.getCompetitorInfo();
    }
}
