package by.vsu.epam.dao.txt;

import java.util.List;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Account;

public class AccountDaoTxtImpl extends BaseDaoTxtImpl<Account> implements AccountDao {
    @Override
    public List<Account> readAll() throws DaoException {
        return read(account -> true);
    }

    @Override
    protected Account convert(String[] cells) throws DaoException {
        if(cells.length == 3) {
            Account account = new Account();
            try {
                account.setId(Long.valueOf(cells[0]));
            } catch(NumberFormatException e) {
                throw new DaoException(String.format("Incorrect accounts file format. Incorrect account identity \"%s\" in line \"%s\"", cells[0], String.join(";", cells)));
            }
            account.setName(cells[1]);
            try {
                account.setBalance(Long.valueOf(cells[2]));
            } catch(NumberFormatException e) {
                throw new DaoException(String.format("Incorrect accounts file format. Incorrect account balance \"%s\" in line \"%s\"", cells[2], String.join(";", cells)));
            }
            return account;
        } else {
            throw new DaoException(String.format("Incorrect accounts file format. Line \"%s\" doesn't contains exactly 3 parts splitted by semicolon", String.join(";", cells)));
        }
    }

    @Override
    protected String[] convert(Account account) {
        return new String[] {
            account.getId().toString(),
            account.getName(),
            account.getBalance().toString()
        };
    }
}
