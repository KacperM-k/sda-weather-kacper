package com.sda.weather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherStackInfo {

    double temperature;
    double pressure;
    double humidity;
    double wind_speed;
    double wind_degree;


}
