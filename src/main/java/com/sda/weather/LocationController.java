package com.sda.weather;

public class LocationController {

    public String addNewLocation (String cityname, String region, String countryname) { // todo longitude and latitude are missing
        Location location = new Location(cityname, region, countryname); // todo just pass this data to a service layer, this object is unnecessary
        return null;
    }
}
