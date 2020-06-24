package dao;

import model.Car;
import model.Engine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CarDAOTest {
    private Car car;
    private CarDAO dao;

    @Test
    public void init() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        try (
                SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            Engine engine = new Engine();
            engine.setModel("diesel");
            engine.setPower(200);
            Car car = new Car();
            car.setMark("Volvo");
            car.setModel("xc60");
            car.setEngine(engine);
            session.persist(car);
            assertThat(session.get(Car.class, 1).getEngine().getPower(), is(200));
            session.getTransaction().commit();
            System.out.println("Power is: " + session.get(Car.class, 1).getEngine().getPower());
        }
    }
}