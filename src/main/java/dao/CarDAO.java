package dao;

import model.Car;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CarDAO implements DAO<Car, Integer>, AutoCloseable {
    /**
     * Connection factory to database.
     */
    private final SessionFactory factory;

    public CarDAO(final SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(final Car car) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
        }
    }

    @Override
    public Car readById(final Integer id) {
        try (final Session session = factory.openSession()) {
            final Car result = session.get(Car.class, id);
            if (result != null) {
                Hibernate.initialize(result.getEngine());
            }
            return result;
        }
    }

    @Override
    public Car readByName(final String name) {
        try (final Session session = factory.openSession()) {
            Car result = (Car) session.get(name, Car.class);
            if (result != null) {
                Hibernate.initialize(result.getEngine());
            }
            return result;
        }
    }

    @Override
    public void update(final Car car) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(final Car car) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(car);
            session.getTransaction().commit();
        }
    }

    @Override
    public void close() throws Exception {
        factory.close();
    }
}