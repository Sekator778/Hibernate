package dao;

import model.Car;
import model.Engine;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CarDAOTest {
    private DAO<Car, Integer> dao;
    @Before
    public void init() {
        SessionFactory factory = null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
            dao = new CarDAO(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        Car car = new Car();
        car.setModel("new model");
        car.setMark("new mark");
        Engine engine = new Engine();
        engine.setModel("new engine");
        engine.setPower(900);
        car.setEngine(engine);
        dao.create(car);
        System.out.println("Created: " + dao.readById(2));
    }

    @Test
    public void read() throws Exception {
        final Car readById = dao.readById(1);
        System.out.println("Read: " + readById);
        dao.close();
        assertThat(readById.getMark(), is("new mark"));
    }

    @Test
    public void update() throws Exception {
        final Car result = dao.readById(1);
        result.setModel("Mazda");
        result.getEngine().setPower(500);
        result.getEngine().setModel("Super engine");
        dao.update(result);
        assertThat(dao.readById(1).getModel(), is("Mazda"));
        System.out.println("Updated: " + dao.readById(1));
        dao.close();
    }

    @Test
    public void delete() {
        Car car = new Car();
        car.setModel("new model");
        car.setMark("new mark");
        car.setId(2);
        Engine engine = new Engine();
        engine.setModel("new engine");
        engine.setPower(900);
        engine.setId(2);
        car.setEngine(engine);
        dao.delete(car);
    }
}