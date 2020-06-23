package dao;

public interface DAO<Entity, Key> {
    boolean create(Entity entity);
    Entity readById(Key key);
    Entity readByName(String name);
    void update(Entity entity);
    boolean delete(Entity entity);
    void close() throws Exception;
}