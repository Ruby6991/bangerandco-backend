package com.apiit.bangerandco.services;

import com.apiit.bangerandco.models.CompetitorVehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
public class WebScraperService {

    public ResponseEntity<List<CompetitorVehicle>> getCompetitorInfo() {
        List<CompetitorVehicle> competitorInfo = new ArrayList<>();
        try{
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("https://www.expressrentacar.co.uk/car-finder/").get();

            // Get the list of vehicles
            Elements vehicles = doc.getElementsByClass("container_cars");

            /**
             * For each vehicle, extract the following information:
             * 1. Model
             * 2. Vehicle Rate
             * 3. Vehicle Category
             * 4. Vehicle Image
             */
            for (Element vehicle : vehicles) {
                CompetitorVehicle competitorVehicle = new CompetitorVehicle();

                // Extract the title
                String vehicleModel = vehicle.select("[itemprop=name]").text();

                // Extract the number of issues on the repository
                String vehicleRate = vehicle.select("[itemprop=price]").text();

                // Extract the description of the repository
                String vehicleCategory = vehicle.select("[itemprop=model]").text();

                String vehicleSrc = vehicle.getElementsByTag("img").attr("src");
                String vehicleImg = "https://www.expressrentacar.co.uk"+vehicleSrc.substring(2);

                competitorVehicle.setVehicleModel(vehicleModel);
                competitorVehicle.setRates(vehicleRate);
                competitorVehicle.setCategory(vehicleCategory);
                competitorVehicle.setVehicleImage(vehicleImg);

                competitorInfo.add(competitorVehicle);
            }
            return new ResponseEntity<>(competitorInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);

    }
}
