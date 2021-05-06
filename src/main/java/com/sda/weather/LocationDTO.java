package com.sda.weather;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationDTO {
    String cityname;
    double temperature;
    double pressure;
    double humidity;
    String wind;


}
