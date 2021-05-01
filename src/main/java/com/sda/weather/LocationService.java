package com.sda.weather;

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

        String uri1 = "http://api.openweathermap.org/data/2.5/weather?q="+location.getCityname()+"&appid=4bd569befe2b8c41377df8867200bc9e";
        String json = makeRequest(uri1);



        return json;


    }

    private String makeRequest(String uri) {
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
