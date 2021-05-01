package com.sda.weather;

import java.util.List;

public interface LocationRepository {

    Location save (Location location);

    List<Location> showAllLocations();

    Location getLocation(Long id, String cityname);
}
