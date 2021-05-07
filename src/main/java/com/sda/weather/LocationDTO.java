package com.sda.weather;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    String cityname;
    double temperature;
    double pressure;
    double humidity;
    double windspeed;
    double winddegree;

    @Override
    public String toString() {
        String wind = String.format("Speed: %.2f m/s, degree: %.2f ", windspeed, winddegree);

//        String.format("Information about weather in " + cityname + ": \n" +
//                "temperature: " + temperature + " C\n" +
//                "pressure: " + pressure + " hPa\n" +
//                "humidity: %2.f" + humidity + " %\n" +
//                "wind: " + wind);

        return String.format("Information about weather in %s:\n" +
                "temperature: %.2f C\n" +
                "pressure: %.2f hPa\n" +
                "humidity: %.2f \n" +
                "wind: %s ",cityname, temperature, pressure, humidity, wind );
    }
}
