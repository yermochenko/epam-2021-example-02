package by.vsu.epam.ioc;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.dao.memory.Repository;
import by.vsu.epam.dao.memory.RepositorySerializationImpl;
import by.vsu.epam.dao.memory.serialization.AccountDaoSerializationImpl;
import by.vsu.epam.dao.memory.serialization.TransferDaoSerializationImpl;

public class ContextSerializationImpl extends ContextImpl {
    @Override
    public AccountDao getAccountDao() {
        AccountDaoSerializationImpl accountDao = new AccountDaoSerializationImpl();
        accountDao.setRepository(getRepository());
        return accountDao;
    }

    @Override
    public TransferDao getTransferDao() {
        TransferDaoSerializationImpl transferDao = new TransferDaoSerializationImpl();
        transferDao.setRepository(getRepository());
        return transferDao;
    }

    private Repository repository;
    @Override
    public Repository getRepository() {
        if(this.repository == null) {
            RepositorySerializationImpl repository = RepositorySerializationImpl.getInstance();
            repository.init("accounts.bin", "transfers.bin");
            this.repository = repository;
        }
        return this.repository;
    }
}
