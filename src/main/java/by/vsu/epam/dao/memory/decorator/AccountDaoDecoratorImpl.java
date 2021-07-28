package by.vsu.epam.dao.memory.decorator;

import java.util.List;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Account;

public class AccountDaoDecoratorImpl extends DaoDecoratorImpl<Account, AccountDao> implements AccountDao {
    @Override
    public List<Account> readAll() throws DaoException {
        return getImpl().readAll();
    }
}
