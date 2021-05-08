package com.sda.weather;

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
    private Double wind_speed;  // todo rename to windSpeed
    private Double wind_degree; // todo rename to windDegree
    private Instant createDate;
    @ManyToOne
    Location location;

    public Weather(Double temperature, Double pressure, Double humidity, Double wind_speed, Double wind_degree, Instant createDate) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_degree = wind_degree;
        this.createDate = createDate;
    }
}
