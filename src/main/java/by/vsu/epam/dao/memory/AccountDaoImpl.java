package by.vsu.epam.dao.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.domain.Account;

public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {
    @Override
    public List<Account> readAll() {
        List<Account> accounts = new ArrayList<>(getMap().values());
        Collections.sort(accounts, (account1, account2) -> Long.compare(account1.getId(), account2.getId()));
        return accounts;
    }

    @Override
    protected Map<Long, Account> getMap() {
        return getRepository().getAccounts();
    }
}
