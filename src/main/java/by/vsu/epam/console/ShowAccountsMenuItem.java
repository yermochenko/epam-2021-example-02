package by.vsu.epam.console;

import java.util.List;
import java.util.Scanner;

import by.vsu.epam.domain.Account;
import by.vsu.epam.service.AccountService;
import by.vsu.epam.service.ServiceException;

public class ShowAccountsMenuItem extends MenuItem {
    private AccountService accountService;

    public ShowAccountsMenuItem() {
        super("Показать список счетов");
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean doWork(Scanner scanner) throws ServiceException {
        List<Account> accounts = accountService.findAll();
        if(!accounts.isEmpty()) {
            System.out.println("Список открытых счетов:");
            for(Account account : accounts) {
                System.out.printf(
                    "    [%04d] %s: %d руб. %02d коп.\n",
                    account.getId(),
                    account.getName(),
                    account.getBalance() / 100,
                    account.getBalance() % 100
                );
            }
            System.out.printf("Всего счетов: %d.\n", accounts.size());
        } else {
            System.out.println("Нет открытых счетов");
        }
        return false;
    }
}
