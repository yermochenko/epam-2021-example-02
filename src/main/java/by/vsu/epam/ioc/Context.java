package by.vsu.epam.ioc;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;

public interface Context {
    AccountDao getAccountDao();
    TransferDao getTransferDao();
}
