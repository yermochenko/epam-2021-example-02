package by.vsu.epam.dao.memory.decorator;

import by.vsu.epam.dao.Dao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Entity;

public abstract class DaoDecoratorImpl<T extends Entity, D extends Dao<T>> implements Dao<T> {
    private D impl;
    private static final double THRESHOLD = 0.01;

    protected D getImpl() throws DaoException {
        if(Math.random() < THRESHOLD) {
            throw new DaoException();
        }
        return impl;
    }

    public void setImpl(D impl) {
        this.impl = impl;
    }

    public Long create(T entity) throws DaoException {
        return getImpl().create(entity);
    }

    public T read(Long id) throws DaoException {
        return getImpl().read(id);
    }

    public void update(T entity) throws DaoException {
        getImpl().update(entity);
    }

    public void delete(Long id) throws DaoException {
        getImpl().delete(id);
    }
}
