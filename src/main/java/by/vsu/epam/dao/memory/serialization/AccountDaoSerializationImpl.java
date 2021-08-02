package by.vsu.epam.dao.memory.serialization;

import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.memory.AccountDaoImpl;
import by.vsu.epam.dao.memory.RepositoryException;
import by.vsu.epam.domain.Account;

public class AccountDaoSerializationImpl extends AccountDaoImpl {
    @Override
    public Long create(Account account) throws DaoException {
        try {
            Long id = super.create(account);
            getRepository().saveAccounts();
            return id;
        } catch(RepositoryException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Account account) throws DaoException {
        try {
            super.update(account);
            getRepository().saveAccounts();
        } catch(RepositoryException e) {
            throw new DaoException();
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try {
            super.delete(id);
            getRepository().saveAccounts();
        } catch(RepositoryException e) {
            throw new DaoException();
        }
    }
}
