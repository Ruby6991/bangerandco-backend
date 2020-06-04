package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.UtilityDTO;
import com.apiit.bangerandco.models.CompetitorVehicle;
import com.apiit.bangerandco.models.Utility;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.jsoup.Connection;
import org.jsoup.nodes.FormElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
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

            // Get the list of repositories
            Elements vehicles = doc.getElementsByClass("container_cars");

            /**
             * For each repository, extract the following information:
             * 1. Title
             * 2. Number of issues
             * 3. Description
             */
            for (Element repository : vehicles) {
                CompetitorVehicle competitorVehicle = new CompetitorVehicle();

                // Extract the title
                String vehicleModel = repository.select("[itemprop=name]").text();

                // Extract the number of issues on the repository
                String vehicleRate = repository.select("[itemprop=price]").text();

                // Extract the description of the repository
                String vehicleCategory = repository.select("[itemprop=model]").text();

                String vehicleSrc = repository.getElementsByTag("img").attr("src");
                String vehicleImg = "https://www.expressrentacar.co.uk/"+vehicleSrc.substring(2);


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
