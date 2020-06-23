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
    private CarDAO dao;

    @Before
    public void init() {
        car = new Car();
        car.setMark("Volvo");
        car.setModel("xc60");
        Engine engine = new Engine();
        engine.setModel("Diesel");
        engine.setPower(200);
        car.setEngine(engine);
        dao = CarDAO.getInstance();
        dao.create(car);
    }

    @After
    public void shutdownTest() {
        dao = CarDAO.getInstance();
        dao.delete(car);
    }

    @Test
    public void read() {
        dao = CarDAO.getInstance();
        assertThat(dao.readById(car.getId()).getModel(), is("xc60"));
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

}