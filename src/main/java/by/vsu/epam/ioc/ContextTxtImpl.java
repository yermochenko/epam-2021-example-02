package by.vsu.epam.ioc;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.dao.txt.AccountDaoTxtImpl;
import by.vsu.epam.dao.txt.TransferDaoTxtImpl;

public class ContextTxtImpl extends ContextImpl {
    @Override
    public AccountDao getAccountDao() {
        AccountDaoTxtImpl accountDao = new AccountDaoTxtImpl();
        accountDao.setFileName("accounts.txt");
        return accountDao;
    }

    @Override
    public TransferDao getTransferDao() {
        TransferDaoTxtImpl transferDao = new TransferDaoTxtImpl();
        transferDao.setFileName("transfers.txt");
        return transferDao;
    }
}
