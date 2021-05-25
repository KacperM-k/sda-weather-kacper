package com.sda.weather.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.weather.weather.Weather;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "location")
    @JsonIgnore
    private Set<Weather> weatherList = new HashSet<>();

    public Location(String city, String country, Double longitude, Double latitude) {
        this.cityname = city;
        this.countryname = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void addWeatherInfo(Weather weather) {
        weatherList.add(weather);
//        weather.setLocation(this);
    }
}
