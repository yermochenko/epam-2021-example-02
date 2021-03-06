package by.vsu.epam.service;

import java.util.Date;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public class TransferServiceImpl implements TransferService {
    private AccountDao accountDao;
    private TransferDao transferDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @Override
    public void transfer(Long srcId, Long destId, Long summ) throws ServiceException {
        try {
            Account src = accountDao.read(srcId);
            if(src != null) {
                Account dest = accountDao.read(destId);
                if(dest != null) {
                    if(src.getBalance() >= summ) {
                        src.setBalance(src.getBalance() - summ);
                        accountDao.update(src);
                        dest.setBalance(dest.getBalance() + summ);
                        accountDao.update(dest);
                        Transfer transfer = new Transfer();
                        transfer.setSrc(src);
                        transfer.setDest(dest);
                        transfer.setSumm(summ);
                        transfer.setDate(new Date());
                        transferDao.create(transfer);
                    } else {
                        throw new NotEnoughMoneyExistsException();
                    }
                } else {
                    throw new AccountNotExistsException(destId);
                }
            } else {
                throw new AccountNotExistsException(srcId);
            }
        } catch(DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void putCash(Long accountId, Long summ) throws ServiceException {
        try {
            Account account = accountDao.read(accountId);
            if(account != null) {
                account.setBalance(account.getBalance() + summ);
                accountDao.update(account);
                Transfer transfer = new Transfer();
                transfer.setSrc(null);
                transfer.setDest(account);
                transfer.setSumm(summ);
                transfer.setDate(new Date());
                transferDao.create(transfer);
            } else {
                throw new AccountNotExistsException(accountId);
            }
        } catch(DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void getCash(Long accountId, Long summ) throws ServiceException {
        try {
            Account account = accountDao.read(accountId);
            if(account != null) {
                if(account.getBalance() >= summ) {
                    account.setBalance(account.getBalance() - summ);
                    accountDao.update(account);
                    Transfer transfer = new Transfer();
                    transfer.setSrc(account);
                    transfer.setDest(null);
                    transfer.setSumm(summ);
                    transfer.setDate(new Date());
                    transferDao.create(transfer);
                } else {
                    throw new NotEnoughMoneyExistsException();
                }
            } else {
                throw new AccountNotExistsException(accountId);
            }
        } catch(DaoException e) {
            throw new ServiceException(e);
        }
    }
}
