package com.sda.weather;

import com.sda.weather.location.LocationController;
import com.sda.weather.location.LocationRepository;
import com.sda.weather.location.LocationRepositoryImpl;
import com.sda.weather.location.LocationService;
import com.sda.weather.weather.WeatherController;
import com.sda.weather.weather.WeatherRepository;
import com.sda.weather.weather.WeatherRepositoryImpl;
import com.sda.weather.weather.WeatherService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class WeatherApplication {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        LocationRepository locationRepository = new LocationRepositoryImpl(sessionFactory);
        LocationService locationService = new LocationService(locationRepository);
        LocationController locationController = new LocationController(locationService);

        WeatherRepository weatherRepository = new WeatherRepositoryImpl(sessionFactory);
        WeatherService weatherService = new WeatherService(weatherRepository);
        WeatherController weatherController = new WeatherController(weatherService);

        UserInterface userInterface = new UserInterface(locationController, weatherController);
        userInterface.runAplication();

    }
}
