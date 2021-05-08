package com.sda.weather;

import java.util.List;

public interface LocationRepository {

    Location save (Location location);

    List<Location> showAllLocations();

    public void addWeatherInfoToLocation(Location location, Weather weather);

    public Location getLocation(Long id, String cityname);
}
