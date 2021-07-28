package by.vsu.epam.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private TransferDao transferDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @Override
    public List<Account> findAll() throws ServiceException {
        try {
            return accountDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Account findById(Long id) throws ServiceException {
        try {
            Account account = accountDao.read(id);
            if(account != null) {
                Date end = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(end);
                calendar.add(Calendar.MONTH, -3);
                Date begin = calendar.getTime();
                List<Transfer> history = transferDao.readByAccount(id, begin, end);
                account.setHistory(history);
            }
            return account;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void openNewAccount(Account account) throws ServiceException {
        try {
            account.setBalance(0L);
            accountDao.create(account);
        } catch(DaoException e) {
            throw new ServiceException(e);
        }
    }
}
