package com.sda.weather.weather;

import com.sda.weather.location.Location;

public interface WeatherRepository {

//    public void addWeatherInfoToLocation(Location location, Weather weather);

    public Location getLocation(Long id);

}
