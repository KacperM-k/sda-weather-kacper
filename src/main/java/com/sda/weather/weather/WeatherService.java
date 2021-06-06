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
import java.text.ParseException;
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

    public List<Weather> getInfoAboutWeatherForecast(Long id, String period) {
        List<Weather> weatherList = new ArrayList<>();
        Location location = weatherRepository.getLocation(id);

        if (location == null) {
            throw new RuntimeException("This city is not in the database");
        }

        if (id == null) {
            throw new RuntimeException("Incorrect data: id or city name required.");

        } else {
            String uri = "https://api.openweathermap.org/data/2.5/onecall?lat=" + location.getLatitude() +
                    "&lon=" + location.getLongitude() + "&exclude=current,hourly,minutely,alert&appid=4bd569befe2b8c41377df8867200bc9e";
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                OpenWeatherInfo openWeatherInfo = objectMapper.readValue(getResponseBody(uri), OpenWeatherInfo.class);

                if (checkIsStringANumber(period)) {
                    int days = Integer.parseInt(period);

                    if (days < 1 || days > 7) {
                        throw new RuntimeException("You can check the weather forecast only for 7 days ahead ");
                    } else {

                        for (int i = 1; i <= days; i++) {
                            double KELVIN_CONST = 273.15;
                            double temperature = openWeatherInfo.getDaily()[i].getTemp().getDay() - KELVIN_CONST;
                            double pressure = openWeatherInfo.getDaily()[i].getPressure();
                            double humidity = openWeatherInfo.getDaily()[i].getHumidity();
                            double windSpeed = openWeatherInfo.getDaily()[i].getWind_speed();
                            double windDegree = openWeatherInfo.getDaily()[i].getWind_deg();
                            long time = openWeatherInfo.getDaily()[i].getDt();
                            String date = changeUnixToDetailedDate(time);

                            Weather weather = new Weather(temperature, pressure, humidity, windSpeed, windDegree, date);
                            weatherRepository.addWeatherInfoToDatabase(location, weather);
                            weatherList.add(weather);
                        }
                    }
                } else if (checkIsStringADate(period)) {
                    //Wyciąganie pogody z danego dnia bez wykorzystania dedykowanego API.

                    Instant actualTime = getActualDateTime();
                    Instant actualTimePlusSeven = getActualDateTime().plusSeconds(86400 * 7);
                    Long userTime = changeStringDateToSeconds(period);

                    if(actualTime.isAfter(Instant.ofEpochSecond(userTime)) || actualTimePlusSeven.isBefore(Instant.ofEpochSecond(userTime))) {
                        throw new RuntimeException("Date is out of range " + actualTime + " - " + actualTimePlusSeven);
                    }

                    for (int i = 1; i <= 7; i++) {
                        double KELVIN_CONST = 273.15;
                        double temperature = openWeatherInfo.getDaily()[i].getTemp().getDay() - KELVIN_CONST;
                        double pressure = openWeatherInfo.getDaily()[i].getPressure();
                        double humidity = openWeatherInfo.getDaily()[i].getHumidity();
                        double windSpeed = openWeatherInfo.getDaily()[i].getWind_speed();
                        double windDegree = openWeatherInfo.getDaily()[i].getWind_deg();
                        long time = openWeatherInfo.getDaily()[i].getDt();
                        String date = changeUnixToDetailedDate(time);

                        if(changeUnixToDate(time).equals(period)) {
                            Weather weather = new Weather(temperature, pressure, humidity, windSpeed, windDegree, date);
                            weatherRepository.addWeatherInfoToDatabase(location, weather);
                            weatherList.add(weather);
                            break;
                        }
                    }
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

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

    private String changeUnixToDetailedDate(long unix) {
        Date date = new Date(unix * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return sdf.format(date);
    }

    private String changeUnixToDate(long unix) {
        Date date = new Date(unix * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return sdf.format(date);
    }

    private Long changeStringDateToSeconds(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(strDate).getTime()/1000;

        } catch (ParseException e) {
            throw new RuntimeException("Data wykracz poza zakres 7 dni");
        }
    }

    private Boolean checkIsStringADate(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(string);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private Boolean checkIsStringANumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

}
