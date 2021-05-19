package com.sda.weather.weather;

import com.sda.weather.location.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class WeatherRepositoryImpl implements WeatherRepository {

    private SessionFactory sessionFactory;

    public WeatherRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addWeatherInfoToDatabase(Location location, List<Weather> weatherList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        for (int i = 0; i < weatherList.size(); i++) {
            location.addWeatherInfo(weatherList.get(i));
        }

        session.update(location);

        transaction.commit();
        session.close();

    }

    @Override
    public Location getLocation(Long id, Integer days) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Location getlocation = session.createQuery("Select l FROM Location AS l WHERE l.id = :id", Location.class)
                .setParameter("id", id)
                .getSingleResult();

        transaction.commit();
        session.close();

        return getlocation;
    }

}
