package com.sda.weather.gsonclasses.open_weather_api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Main {
    private double temp;
    private double pressure;
    private double humidity;
}
