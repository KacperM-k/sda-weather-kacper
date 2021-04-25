package com.sda.weather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String cityname;
    String region;
    @Column(nullable = false)
    String countryname;
    @Column(nullable = false)
    Double longitude;
    @Column(nullable = false)
    Double latitude;


    public Location( String cityname, String countryname, Double longitude, Double latitude) {
        this.cityname = cityname;
        this.countryname = countryname;
        this. longitude = longitude;
        this.latitude = latitude;

    }

}
