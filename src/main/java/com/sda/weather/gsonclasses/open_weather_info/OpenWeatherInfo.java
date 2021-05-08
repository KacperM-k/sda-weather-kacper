package com.sda.weather.gsonclasses.open_weather_info;

import com.sda.weather.gsonclasses.open_weather_info.Main;
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
