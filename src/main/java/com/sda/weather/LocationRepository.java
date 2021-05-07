package com.sda.weather;

import java.util.List;

public interface LocationRepository {

    Location save (Location location);

    void addWeatherInfoToLocation(Location location, Weather weather);

    List<Location> showAllLocations();

    Location getLocation(Long id, String cityname);
}
