package by.vsu.epam.dao;

import java.util.List;

import by.vsu.epam.domain.Account;

public interface AccountDao extends Dao<Account> {
    List<Account> readAll() throws DaoException;
}
