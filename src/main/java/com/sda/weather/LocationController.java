package com.sda.weather;

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


    public List<String> showAllLocations() {
        List<Location> locations = locationService.showAllLocations();
//      List<String> jasons = null;
//
//      locations.stream()
//               .map(l -> jasons.add(String.format("{\"id\": %s, \"cityname\": \"%s\", \"region\": \"%s\", \"countryname\": \"%s\", \"longitude\": \"%s\", \"latitude\": \"%s\"}",
//                        l.getId(), l.getCityname(), l.getRegion(), l.getCountryname(), l.getLongitude(), l.getLatitude())));
//
//        return jasons;
        return null;
    }
}
