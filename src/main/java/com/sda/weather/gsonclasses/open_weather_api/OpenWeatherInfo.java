package com.sda.weather.gsonclasses.open_weather_api;

import com.sda.weather.gsonclasses.open_weather_api.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenWeatherInfo {

    private Main main;
    private Wind wind;

//    private double temp;
//    private double pressure;
//    private double humidity;
//    private double speed;
//    private double deg;


}
