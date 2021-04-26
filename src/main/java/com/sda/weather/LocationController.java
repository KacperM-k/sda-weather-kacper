package com.sda.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public String addNewLocation(String cityname, String region, String countryname, Double longitude, Double latitude) {

        Location newLocation = locationService.createNewLocation(cityname, region, countryname, longitude, latitude);

        return String.format("{\"id\": %s, \"cityname\": \"%s\", \"region\": \"%s\", \"countryname\": \"%s\", \"longitude\": \"%s\", \"latitude\": \"%s\"}",
                newLocation.getId(), newLocation.getCityname(), newLocation.getRegion(), newLocation.getCountryname(), newLocation.getLongitude(), newLocation.getLatitude());
    }


    public String showAllLocations() {
        List<Location> locations = locationService.showAllLocations();
        ObjectMapper objectMapper = new ObjectMapper();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(locations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}

