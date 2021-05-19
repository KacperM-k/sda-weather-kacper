package com.sda.weather.weather;

import com.sda.weather.location.Location;

import java.util.List;

public interface WeatherRepository {

    void addWeatherInfoToDatabase(Location location, List<Weather> weatherList);

    Location getLocation(Long id, Integer days);

}
