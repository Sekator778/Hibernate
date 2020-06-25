package model;//package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CarTest {
    private Engine engine1;
    private Engine engine2;
    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    private Driver driver1;
    private Driver driver2;
    private Driver driver3;
    private final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    private final SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
    private final Session session = sessionFactory.openSession();

    @Before
    public void init() {
        engine1 = new Engine("diesel", 200);
        engine2 = new Engine("hibrid", 123);

        car1 = new Car("Volvo", "xc90", engine2);
        car2 = new Car("Mazda", "6", engine1);
        car3 = new Car("Opel", "combo", engine1);
        car4 = new Car("Toyota", "Auris", engine2);

        driver1 = new Driver("Alex", Set.of(car1, car2));
        driver2 = new Driver("Olga", Set.of(car3, car4)); //car3 only
        driver3 = new Driver("Bob", Set.of(car1, car4));

        session.beginTransaction();
        session.persist(engine1);
        session.persist(engine2);
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);
        session.persist(car4);
        session.persist(driver1);
        session.persist(driver2);
        session.persist(driver3);
        session.getTransaction().commit();
    }

//    @After
//    public void close() {
//        session.close();
//    }

    @Test
    public void create() {
        session.beginTransaction();
        Query q = session.createQuery("select d From Driver d where d.name like :driverName");
        q.setParameter("driverName", "Alex");
        Driver driver = (Driver) q.getSingleResult();
        session.getTransaction().commit();
        assertThat(driver.getCars(), is(Set.of(this.car1, this.car2)));
    }

    @Test
    public void edit() {
        session.beginTransaction();
        Query q = session.createQuery("select d From Driver d where d.name like :driverName");
        q.setParameter("driverName", "Alex");
        Driver driver = (Driver) q.getSingleResult();
        Set<Car> cars = new HashSet<>(driver.getCars());
        cars.remove(this.car1);
        driver.setCars(cars);
        session.update(driver);
        session.getTransaction().commit();
        assertThat(driver.getCars(), is(Set.of(this.car2)));
    }

    @Test
    public void delete() {
        session.beginTransaction();
        Query query = session.createQuery("delete From Driver d where d.name like :driver");
        query.setParameter("driver", "Olga");
        query.executeUpdate();
        Query q = session.createQuery("select c From Car c where c.model like :model");
        q.setParameter("model", "combo");
        Car result = (Car) q.getSingleResult();
        Set<Driver> drivers = result.getDrivers();
        session.getTransaction().commit();
        assertNull(drivers);
    }
}
