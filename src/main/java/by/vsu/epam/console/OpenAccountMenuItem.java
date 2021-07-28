package by.vsu.epam.console;

import java.util.Scanner;

import by.vsu.epam.domain.Account;
import by.vsu.epam.service.AccountService;
import by.vsu.epam.service.ServiceException;

public class OpenAccountMenuItem extends MenuItem {
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public OpenAccountMenuItem() {
        super("Открыть новый счёт");
    }

    @Override
    public boolean doWork(Scanner scanner) throws ServiceException {
        Account account = new Account();
        System.out.print("Введите имя владельца счёта: ");
        String name = scanner.nextLine();
        account.setName(name);
        accountService.openNewAccount(account);
        System.out.printf("Новый счёт с номером [%04d] успешно открыт", account.getId());
        return false;
    }
}
