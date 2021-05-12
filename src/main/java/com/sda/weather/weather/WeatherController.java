package com.sda.weather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class WeatherController {

    WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public String showInfoAboutWeather(Long id) {
        try {
            List<Weather> weather = weatherService.getInfoAboutWeather(id);
            return objectMapper.writeValueAsString(weather);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

}
