package db;

import java.util.List;

public interface Dao<E> {

    void insert(E entity);

    void update(E entity);

    boolean delete(E entity);

    List<E> getAll();

    E getEmployee (Object aValue);


}
