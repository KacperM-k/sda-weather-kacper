package com.sda.weather;

public class LocationService {

    private LocationRepositoryImpl locationRepository;

    public LocationService(LocationRepositoryImpl locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createNewLocation(String cityname, String region, String countryname) {
        if (cityname == null) {
            throw new RuntimeException("City name is required");
        }
        if (countryname == null) {
            throw new RuntimeException("Country name is required");
        }

        if (region == null) {
            Location location = new Location(cityname, countryname);    // todo always call this constructor
            Location savedLocation = locationRepository.save(location);
            return savedLocation;
        }

        // todo if region is not null then use location.setRegion(region)

        Location location = new Location(cityname, region, countryname);
        Location savedLocation = locationRepository.save(location);
        return savedLocation;
    }
}
