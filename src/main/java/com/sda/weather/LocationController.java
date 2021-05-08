package com.sda.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class LocationController {

    private LocationService locationService;
    ObjectMapper objectMapper = new ObjectMapper();

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public String addNewLocation(String cityname, String region, String countryName, Double longitude, Double latitude) {
        Location newLocation = locationService.createNewLocation(cityname, region, countryName, longitude, latitude);
        String json = null;

        try {
            json = objectMapper.writeValueAsString(newLocation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String showAllLocations() {
        List<Location> locations = locationService.showAllLocations();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(locations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String showInfoAboutWeather(Long id, String cityname){
        return locationService.getInfoAboutWeather(id, cityname);
    }

}

