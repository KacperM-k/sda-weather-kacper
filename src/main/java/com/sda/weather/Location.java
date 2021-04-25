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
//    @Column(nullable = false)


    public Location( String cityname, String region, String countryname) {
        this.cityname = cityname;
        this.region = region;
        this.countryname = countryname;
    }

    public Location(String cityname, String countryname) {
        this.cityname = cityname;
        this.countryname = countryname;
    }
}
