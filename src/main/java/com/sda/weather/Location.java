package com.sda.weather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cityname;
    private String region;
    @Column(nullable = false)
    private String countryname;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private Double latitude;

    public Location(String cityname, String countryname, Double longitude, Double latitude) {
        this.cityname = cityname;
        this.countryname = countryname;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
