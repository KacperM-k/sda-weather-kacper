package com.sda.weather.gsonclasses.open_weather_api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenWeatherInfo {

    private Main main;
    private Wind wind;
}
