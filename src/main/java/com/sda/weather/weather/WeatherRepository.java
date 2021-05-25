package com.sda.weather.weather;

import com.sda.weather.location.Location;

import java.util.List;

public interface WeatherRepository {

    void addWeatherInfoToDatabase(Location location, Weather weather);

    Location getLocation(Long id);
}
