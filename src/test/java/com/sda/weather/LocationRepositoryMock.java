package com.sda.weather;

import com.sda.weather.location.Location;
import com.sda.weather.location.LocationRepository;

import java.util.List;

public class LocationRepositoryMock implements LocationRepository {


    @Override
    public Location save(Location location) {
        location.setId(1L);
        return location;
    }

    @Override
    public List<Location> showAllLocations() {
        return null;
    }

}
