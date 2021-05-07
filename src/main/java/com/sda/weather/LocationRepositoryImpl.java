package com.sda.weather;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LocationRepositoryImpl implements LocationRepository {

    private SessionFactory sessionFactory;

    public LocationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Location save(Location location) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(location);

        transaction.commit();
        session.close();

        return location;
    }

    @Override
    public void addWeatherInfoToLocation(Location location, Weather weather) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        location.addWeatherInfo(weather);

        transaction.commit();
        session.close();


    }

    @Override
    public List<Location> showAllLocations() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Location> locations = session.createQuery("SELECT l FROM Location AS l", Location.class)
                .getResultList();

        transaction.commit();
        session.close();

        return locations;
    }

    @Override
    public Location getLocation(Long id, String cityname){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Location getlocation = session.createQuery("Select l FROM Location AS l WHERE l.id = :id OR l.cityname = :cityname", Location.class)
                .setParameter("id", id )
                .setParameter("cityname", cityname)
                .getSingleResult();

        transaction.commit();
        session.close();

        return getlocation;

    }
}
