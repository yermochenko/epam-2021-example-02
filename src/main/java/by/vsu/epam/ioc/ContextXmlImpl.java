package by.vsu.epam.ioc;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.dao.xml.AccountDaoXmlImpl;
import by.vsu.epam.dao.xml.TransferDaoXmlImpl;

public class ContextXmlImpl extends ContextImpl {
    @Override
    public AccountDao getAccountDao() {
        AccountDaoXmlImpl accountDao = new AccountDaoXmlImpl();
        accountDao.setFileName("accounts.xml");
        return accountDao;
    }

    @Override
    public TransferDao getTransferDao() {
        TransferDaoXmlImpl transferDao = new TransferDaoXmlImpl();
        transferDao.setFileName("transfers.xml");
        return transferDao;
    }
}
