package com.sda.weather.weather;

import com.sda.weather.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Double temperature;
    private Double pressure;
    private Double humidity;
    private Double windSpeed;
    private Double windDegree;
    private String date;
    @ManyToOne
    Location location;

    public Weather(Double temperature, Double pressure, Double humidity, Double wind_speed, Double wind_degree, String date) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = wind_speed;
        this.windDegree = wind_degree;
        this.date = date;
    }
}
