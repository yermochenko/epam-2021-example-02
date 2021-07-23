package by.vsu.epam.ioc;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.dao.memory.AccountDaoImpl;
import by.vsu.epam.dao.memory.Repository;
import by.vsu.epam.dao.memory.RepositoryImpl;
import by.vsu.epam.dao.memory.TransferDaoImpl;

public class ContextImpl implements Context {
    @Override
    public AccountDao getAccountDao() {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        accountDao.setRepository(getRepository());
        return accountDao;
    }

    @Override
    public TransferDao getTransferDao() {
        TransferDaoImpl transferDao = new TransferDaoImpl();
        transferDao.setRepository(getRepository());
        return transferDao;
    }

    public Repository getRepository() {
        return RepositoryImpl.getInstance();
    }
}
