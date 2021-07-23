package by.vsu.epam;

import java.util.List;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.domain.Account;
import by.vsu.epam.ioc.Context;
import by.vsu.epam.ioc.ContextFactory;

public class TestTransferDao {
    public static void output(List<Account> accounts) {
        System.out.println("********** Список банковских счетов **********");
        for(Account account : accounts) {
            System.out.printf(
                "[%02d] %s, баланс %d руб. %02d коп.\n",
                account.getId(),
                account.getName(),
                account.getBalance() / 100,
                account.getBalance() % 100
            );
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Context context = ContextFactory.newInstance();
        AccountDao dao = context.getAccountDao();
        output(dao.readAll());
        Account account = new Account();
        account.setName("Васильев");
        account.setBalance(12_00L);
        dao.create(account);
        output(dao.readAll());
        account = dao.read(4L);
        account.setBalance(23_00L);
        dao.update(account);
        output(dao.readAll());
        dao.delete(4L);
        output(dao.readAll());
    }
}
