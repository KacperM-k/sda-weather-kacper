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
        try {
            List<Location> locations = locationService.showAllLocations();
            return objectMapper.writeValueAsString(locations);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}

