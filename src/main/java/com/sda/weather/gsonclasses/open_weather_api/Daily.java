package com.sda.weather.gsonclasses.open_weather_api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Daily {
    private long dt;
    private Temp temp;
    private double pressure;
    private double humidity;
    private double wind_speed;
    private double wind_deg;

}
