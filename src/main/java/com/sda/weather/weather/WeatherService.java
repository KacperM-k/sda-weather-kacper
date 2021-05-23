package com.sda.weather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.TimeDTO;
import com.sda.weather.gsonclasses.open_weather_api.OpenWeatherInfo;
import com.sda.weather.location.Location;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WeatherService {

    private WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Weather> getInfoAboutWeatherForecast(Long id, Integer days) {
        List<Weather> weatherList = new ArrayList<>();

        if (id == null) {
            throw new RuntimeException("Incorrect data: id or city name required.");
        }

        if (days < 1 || days > 7) {
            throw new RuntimeException("You can check the weather forecast only for 7 days ahead ");
        }

        Location location = weatherRepository.getLocation(id, days);

        if (location == null) {
            throw new RuntimeException("This city is not in the database");
        }

        String uri1 = "https://api.openweathermap.org/data/2.5/onecall?lat=" + location.getLatitude() +
                "&lon=" + location.getLongitude() + "&exclude=current,hourly,minutely,alert&appid=4bd569befe2b8c41377df8867200bc9e";

//        String uri1 = "http://api.openweathermap.org/data/2.5/weather?q=" + location.getCityname() + "&appid=4bd569befe2b8c41377df8867200bc9e";
//        String uri2 = "http://api.weatherstack.com/current?access_key=8fc1775cc891f959446e8e12c20ae86f&query=" + location.getCityname();

        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OpenWeatherInfo openWeatherInfo = objectMapper.readValue(getResponseBody(uri1), OpenWeatherInfo.class);

            for(int i = 1; i <= days; i++ ) {
                double KELVIN_CONST = 273.15;
                double temperature = openWeatherInfo.getDaily()[i].getTemp().getDay() - KELVIN_CONST;
                double pressure = openWeatherInfo.getDaily()[i].getPressure();
                double humidity = openWeatherInfo.getDaily()[i].getHumidity();
                double windSpeed = openWeatherInfo.getDaily()[i].getWind_speed();
                double windDegree = openWeatherInfo.getDaily()[i].getWind_deg();
                long time = openWeatherInfo.getDaily()[i].getDt();
                String date = changeUnixToDate(time);

                Weather weather = new Weather(temperature, pressure, humidity, windSpeed, windDegree, date);
                weatherList.add(weather);
            }

            weatherRepository.addWeatherInfoToDatabase(location, weatherList);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return weatherList;
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

    private String changeUnixToDate(long unix){
        Date date = new Date(unix*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return sdf.format(date);
    }

}
