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
    private Car car1 = new Car();
    private Car car2 = new Car();
    private final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    private final SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
    private final Session session = sessionFactory.openSession();

    @Before
    public void init() {
        Engine engine1 = new Engine("diesel", 200);
        Engine engine2 = new Engine("hibrid", 123);
        Driver driver = new Driver("Aleg");
        Driver driver0 = new Driver("Petr");
        car1 = new Car("Volvo", "xc90", engine2, Set.of(driver));
        car2 = new Car("Mazda", "6", engine1, Set.of(driver, driver0));
        Car car3 = new Car("Opel", "combo", engine1, Set.of(driver0));
        Car car4 = new Car("Toyota", "Auris", engine2);
        Driver driver1 = new Driver("Alex", Set.of(car1, car2));
        Driver driver2 = new Driver("Olga", Set.of(car3, car4));
        Driver driver3 = new Driver("Bob", Set.of(car1, car4));
        session.beginTransaction();
        session.persist(engine1);
        session.persist(engine2);
        session.persist(driver);
        session.persist(driver0);
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);
        session.persist(car4);
        session.persist(driver1);
        session.persist(driver2);
        session.persist(driver3);
        session.getTransaction().commit();
    }
    @After
    public void close() {
        session.close();
    }

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
        Query q1 = session.createQuery("delete from Driver d where d.name like :driverName");
        q1.setParameter("driverName", "Aleg");
        q1.executeUpdate();
        Query q2 = session.createQuery("delete from Driver d where d.name like :driverName");
        q2.setParameter("driverName", "Petr");
        q2.executeUpdate();

        Query q3 = session.createQuery("select c From Car c where c.model like :modelName");
        q3.setParameter("modelName", "6");
        Car car = (Car) q3.getSingleResult();
        Set<Driver> drivers = car.getDrivers();
        session.getTransaction().commit();
        System.out.println("=================================");
       drivers.forEach(System.out::println);
//        assertTrue(drivers.isEmpty());
    }
}
