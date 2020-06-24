package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void whenOnePersonHavetwoAddress() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        try (
                SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            HomeAddress address = new HomeAddress("Essenskaya");
            HomeAddress addressWork = new HomeAddress("Korolyova");
            List<HomeAddress> list = new ArrayList<>();
            list.add(address);
            list.add(addressWork);
            Person person = new Person("Alex", list);
            address.setPerson(person);
            addressWork.setPerson(person);
            session.persist(address);
            session.persist(addressWork);
            session.persist(person);
            session.getTransaction().commit();
            assertEquals(session.get(HomeAddress.class, 1).person,
                    session.get(HomeAddress.class, 2).person);
        }
    }
}