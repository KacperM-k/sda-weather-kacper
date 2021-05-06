package com.sda.weather;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LocationService {

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
        List<Location> locations = locationRepository.showAllLocations();

        locations.forEach(l -> System.out.format("Id: %s, City: %s, Region: %S, Country: %s, Longtiude: %s, Latitiude: %s\n",
                l.getId(), l.getCityname(), l.getRegion(), l.getCountryname(), l.getLongitude(), l.getLatitude()));

        return locations;
    }

    public String getInfoAboutWeather(Long id, String cityname) {
        if (id == null && cityname == null) {
            throw new RuntimeException("Incorrect data: id or cityname required.");
        }

        Location location = locationRepository.getLocation(id, cityname);

        if (location == null) {
            throw new RuntimeException("This city is not in the database");
        }

        String uri1 = "http://api.openweathermap.org/data/2.5/weather?q=" + location.getCityname() + "&appid=4bd569befe2b8c41377df8867200bc9e";
        String uri2 = "http://api.weatherstack.com/current?access_key=8fc1775cc891f959446e8e12c20ae86f&query=" + location.getCityname();

        //TODO wyciągnięcie z json'ów informacji i uśrednienie wartości,
        // sformatowanie danych do wyświetlenia dla użytkownika

        Gson gson = new Gson();
        OpenWeatherInfo openWeatherInfo = gson.fromJson(getResponseBody(uri1), OpenWeatherInfo.class);
        WeatherStackInfo weatherStackInfo = gson.fromJson(getResponseBody(uri2), WeatherStackInfo.class);

        double temperature = (weatherStackInfo.getTemperature() + openWeatherInfo.getTemp()) / 2;
        double pressure = (weatherStackInfo.getPressure() + openWeatherInfo.getPressure()) / 2;
        double humidity = (weatherStackInfo.getHumidity() + openWeatherInfo.getHumidity()) / 2;
        double windspeed = (weatherStackInfo.getWind_speed() + openWeatherInfo.getSpeed()) / 2;
        double winddegree = (weatherStackInfo.getWind_degree() + openWeatherInfo.getDeg()) / 2;
        String wind = "Speed: " + windspeed + ", degree: " + winddegree;

        LocationDTO locationDTO = new LocationDTO(location.getCityname(), temperature, pressure, humidity, wind);

        System.out.println(locationDTO);
        return getResponseBody(uri1)+ "\n" + getResponseBody(uri2);
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
}
