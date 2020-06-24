package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarTest {
    @Test
    public void whenAddTwoCarWithOneEngine() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        try (
                SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            Engine engine = new Engine();
            engine.setModel("diesel");
            engine.setPower(200);
            Engine engine2 = new Engine();
            engine2.setModel("electro");
            engine2.setPower(343);
            Car car = new Car();
            car.setMark("opel");
            car.setModel("combo");
            car.setEngine(engine);
            Car car2 = new Car();
            car2.setMark("Mazda");
            car2.setModel("xedos");
            car2.setEngine(engine);
            session.persist(engine);
            session.persist(engine2);
            session.persist(car);
            session.persist(car2);
            session.getTransaction().commit();
            assertThat(session.get(Car.class, 1).getEngine().getId(), is(
                    session.get(Car.class, 2).getEngine().getId()
            ));
        }
    }
}