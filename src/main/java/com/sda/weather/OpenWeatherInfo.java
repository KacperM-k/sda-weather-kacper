package com.sda.weather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenWeatherInfo {
    double temp;
    double pressure;
    double humidity;
    double speed;
    double deg;


}
