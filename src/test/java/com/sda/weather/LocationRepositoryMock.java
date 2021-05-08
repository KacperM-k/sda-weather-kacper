package com.sda.weather;

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
