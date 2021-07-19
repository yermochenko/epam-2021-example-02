package by.vsu.epam.dao;

import by.vsu.epam.domain.Entity;

public interface Dao <T extends Entity> {
    /** Create */
    Long create(T entity);
    /** Read */
    T read(Long id);
    /** Update */
    void update(T entity);
    /** Delete */
    void delete(Long id);
}
