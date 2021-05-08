package com.sda.weather;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationController {

    private LocationService locationService;
    ObjectMapper objectMapper = new ObjectMapper();

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public String addNewLocation(String cityname, String region, String countryName, Double longitude, Double latitude) {
        Location newLocation = locationService.createNewLocation(cityname, region, countryName, longitude, latitude);

        return String.format("{\"id\": %s, \"cityname\": \"%s\", \"region\": \"%s\", \"countryName\": \"%s\", \"longitude\": \"%s\", \"latitude\": \"%s\"}",
                newLocation.getId(), newLocation.getCityname(), newLocation.getRegion(), newLocation.getCountryname(), newLocation.getLongitude(), newLocation.getLatitude());  // todo use objectMapper
    }
}

