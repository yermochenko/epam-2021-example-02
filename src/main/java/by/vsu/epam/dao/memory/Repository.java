package by.vsu.epam.dao.memory;

import java.util.Map;

import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public interface Repository {
    Map<Long, Account> getAccounts();

    Map<Long, Transfer> getTransfers();

    void saveAccounts() throws RepositoryException;

    void saveTransfers() throws RepositoryException;
}
