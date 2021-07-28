package by.vsu.epam.service;

import java.util.List;

import by.vsu.epam.domain.Account;

public interface AccountService {
    List<Account> findAll() throws ServiceException;

    Account findById(Long id) throws ServiceException;

    void openNewAccount(Account account) throws ServiceException;
}
