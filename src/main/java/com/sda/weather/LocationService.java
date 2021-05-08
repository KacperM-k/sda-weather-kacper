package com.sda.weather;

public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createNewLocation(String cityName, String region, String countryName, Double longitude, Double latitude) {
        if (cityName == null) { // todo check a value cityName.isEmpty() cityName.isBlank()
            throw new RuntimeException("City name is required");
        }
        if (countryName == null) {
            throw new RuntimeException("Country name is required");
        }
        if (longitude == null) {
            throw new RuntimeException("Longitude is required");
        }
        if (longitude < -180 || longitude > 180) {
            throw new RuntimeException("The longitude is invalid, the required value is between -180 and 180");
        }
        if (latitude == null) {
            throw new RuntimeException("Latitude is required");
        }
        if (latitude < -90 || latitude > 90) {
            throw new RuntimeException("The latitude is invalid, the required value is between -90 and 90");
        }

        Location location = new Location(cityName, countryName, longitude, latitude);

        if (region != null) {
            location.setRegion(region);
        }

        Location savedLocation = locationRepository.save(location);

        return savedLocation;
    }
}
