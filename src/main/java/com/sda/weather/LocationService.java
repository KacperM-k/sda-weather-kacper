package com.sda.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.gsonclasses.open_weather_api.OpenWeatherInfo;
import com.sda.weather.gsonclasses.weather_stack_api.WeatherStackInfo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LocationService {

    ObjectMapper objectMapper = new ObjectMapper();

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createNewLocation(String cityname, String region, String countryname, Double longitude, Double latitude) {
        if (cityname == null) {
            throw new RuntimeException("City name is required");
        }
        if (countryname == null) {
            throw new RuntimeException("Country name is required");
        }
        if (longitude == null) {
            throw new RuntimeException("Longitude is required");
        }
        if (longitude < -180 || longitude > 180) {
            throw new RuntimeException("The longitude is invalid, the required value is between -180 and 180");
        }
        if (latitude == null) {
            throw new RuntimeException("Latitude is required");
        }
        if (latitude < -90 || latitude > 90) {
            throw new RuntimeException("The latitude is invalid, the required value is between -90 and 90");
        }

        Location location = new Location(cityname, countryname, longitude, latitude);

        if (region != null) {
            location.setRegion(region);
        }

        Location savedLocation = locationRepository.save(location);

        return savedLocation;
    }


    public List<Location> showAllLocations() {
        return locationRepository.showAllLocations();
    }

    public Weather getInfoAboutWeather(Long id, String cityname) {
        if (id == null && cityname == null) {
            throw new RuntimeException("Incorrect data: id or cityname required.");
        }

        Location location = locationRepository.getLocation(id, cityname);

        if (location == null) {
            throw new RuntimeException("This city is not in the database");
        }

        // todo use https://openweathermap.org/api only
        //  fetch data about forecast
        //  use a forecast for a specific date and time
        //  store it in your Weather object

        String uri1 = "http://api.openweathermap.org/data/2.5/weather?q=" + location.getCityname() + "&appid=4bd569befe2b8c41377df8867200bc9e";
        String uri2 = "http://api.weatherstack.com/current?access_key=8fc1775cc891f959446e8e12c20ae86f&query=" + location.getCityname();

        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OpenWeatherInfo openWeatherInfo = objectMapper.readValue(getResponseBody(uri1), OpenWeatherInfo.class);
            WeatherStackInfo weatherStackInfo = objectMapper.readValue(getResponseBody(uri2), WeatherStackInfo.class);

            double KELVIN_CONST = 273.15;
            double temperature = (openWeatherInfo.getMain().getTemp() - KELVIN_CONST + weatherStackInfo.getCurrent().getTemperature()) / 2;
            double pressure = (openWeatherInfo.getMain().getPressure() + weatherStackInfo.getCurrent().getPressure()) / 2;
            double humidity = (openWeatherInfo.getMain().getHumidity() + weatherStackInfo.getCurrent().getHumidity()) / 2;
            double windspeed = (openWeatherInfo.getWind().getSpeed() + (weatherStackInfo.getCurrent().getWind_speed() * 1000 / 3600)) / 2;
            double winddegree = (openWeatherInfo.getWind().getDeg() + weatherStackInfo.getCurrent().getWind_degree()) / 2;


            Weather weather = new Weather(temperature, pressure, humidity, windspeed, winddegree, getActualDateTime());
//            locationRepository.addWeatherInfoToLocation(location, weather);

            LocationDTO locationDTO = new LocationDTO(location.getCityname(), temperature, pressure, humidity, windspeed, winddegree);
            System.out.println(locationDTO);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null; // todo
    }

    private String getResponseBody(String uri) {
        String responseBody = null;
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(uri))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            responseBody = httpResponse.body();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    private Instant getActualDateTime() {
        Instant instant = null;

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://worldclockapi.com/api/json/utc/now"))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();

            TimeDTO timeDTO = objectMapper.readValue(responseBody, TimeDTO.class);
            String currentDateTime = timeDTO.getCurrentDateTime();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
            LocalDateTime localDateTime = LocalDateTime.parse(currentDateTime, dateTimeFormatter);
            instant = localDateTime.toInstant(ZoneOffset.UTC);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return instant;
    }
}
