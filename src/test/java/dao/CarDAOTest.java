package dao;

import model.Car;
import model.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CarDAOTest {
    private Car car;
    private Car car2;
    private CarDAO dao;

    @Before
    public void init() {
        car = new Car();
        car.setMark("Volvo");
        car.setModel("xc60");
        car2 = new Car();
        car2.setMark("Opel");
        car2.setModel("combo");
        Engine engine = new Engine();
        engine.setModel("Diesel");
        engine.setPower(200);
        car.setEngine(engine);
        car2.setEngine(engine);
        dao = CarDAO.getInstance();
        dao.create(car);
//        dao.create(car2);
    }

    @After
    public void shutdownTest() {
        dao = CarDAO.getInstance();
//        dao.delete(car);
    }

    @Test
    public void read() {
        dao = CarDAO.getInstance();
        assertThat(dao.readById(car.getId()).getEngine().getPower(), is(200));
//        assertThat(dao.readById(car2.getId()).getEngine().getPower(), is(200));
    }

//    @Test
//    public void update() {
//        car.setModel("Mazda");
//        dao.update(car);
//        assertThat(dao.readById(car.getId()).getModel(), is("Mazda"));
//    }
}