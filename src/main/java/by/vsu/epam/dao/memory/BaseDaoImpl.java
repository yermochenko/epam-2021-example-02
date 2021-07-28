package by.vsu.epam.dao.memory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import by.vsu.epam.dao.Dao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Entity;

public abstract class BaseDaoImpl<T extends Entity> implements Dao<T> {
    private Repository repository;

    protected Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Long create(T entity) throws DaoException {
        Map<Long, T> map = getMap();
        Set<Long> keySet = map.keySet();
        Long id = 0L;
        if(!keySet.isEmpty()) {
            id = Collections.max(keySet);
        }
        id++;
        entity.setId(id);
        map.put(id, entity);
        return id;
    }

    @Override
    public T read(Long id) throws DaoException {
        return getMap().get(id);
    }

    @Override
    public void update(T entity) throws DaoException {
        Map<Long, T> map = getMap();
        if(map.containsKey(entity.getId())) {
            map.put(entity.getId(), entity);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        getMap().remove(id);
    }

    protected abstract Map<Long, T> getMap() throws DaoException;
}
