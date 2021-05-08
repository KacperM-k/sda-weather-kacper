package com.sda.weather.gsonclasses.weather_stack_info;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Current {

    private double temperature;
    private double pressure;
    private double humidity;
    private double wind_speed;
    private double wind_degree;
}
