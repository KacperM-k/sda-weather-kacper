package com.sda.weather.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationDTO {
    private Long id;
    private String cityname;
    private String region;
    private String countryname;
    private Double longitude;
    private Double latitude;



}
