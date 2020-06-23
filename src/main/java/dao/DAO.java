package dao;

public interface DAO<Entity, Key> {
    void create(Entity entity);
    Entity readById(Key key);
    Entity readByName(String name);
    void update(Entity entity);
    void delete(Entity entity);
    void close() throws Exception;
}