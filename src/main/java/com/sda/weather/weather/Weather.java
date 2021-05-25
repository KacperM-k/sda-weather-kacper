package com.sda.weather.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.weather.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @JsonIgnore // todo Weather contains Location which contains Weather, which contains Location... and your log message is infinity
    private Location location;

    public Weather(Double temperature, Double pressure, Double humidity, Double windSpeed, Double windDegree, String date) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.date = date;
    }
}
