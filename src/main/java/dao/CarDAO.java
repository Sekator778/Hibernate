package dao;

import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class CarDAO implements DAO<Car, Integer> {
    private static final CarDAO INSTANCE = new CarDAO();

    /**
     * for release singleton
     */
    private CarDAO() {
    }

    /**
     * return our singleton
     *
     * @return instance class HbmTodo
     */
    public static CarDAO getInstance() {
        return INSTANCE;
    }

    /**
     * factory for session hibernate
     */
    private SessionFactory createSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = createSessionFactory().openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean create(final Car car) {
        return tx(
                session -> {
                    session.save(car);
                    return true;
                }
        );
    }

    @Override
    public boolean delete(Car car) {
        return tx(
                session -> {
                    session.delete(car);
                    return true;
                }
        );
    }

    @Override
    public Car readById(Integer integer) {
            return tx(
                    session -> session.createQuery("from " + Car.class.getName(), Car.class).getSingleResult()
            );
        }
    @Override
    public Car readByName(String name) {
        return null;
    }

    @Override
    public void update(Car car) {

    }


    @Override
    public void close() throws Exception {

    }

}