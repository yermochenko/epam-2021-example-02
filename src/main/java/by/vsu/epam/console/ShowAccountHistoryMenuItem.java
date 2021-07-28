package by.vsu.epam.console;

import java.util.List;
import java.util.Scanner;

import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;
import by.vsu.epam.service.AccountService;
import by.vsu.epam.service.ServiceException;

public class ShowAccountHistoryMenuItem extends MenuItem {
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public ShowAccountHistoryMenuItem() {
        super("Показать выписку по счёту");
    }

    @Override
    public boolean doWork(Scanner scanner) throws ServiceException {
        try {
            System.out.print("Введите номер счёта: ");
            Long id = Long.valueOf(scanner.nextLine());
            Account account = accountService.findById(id);
            if(account != null) {
                System.out.printf(
                    "Выписка по счёту [%04d].\n" +
                    "Владелец счёта: %s\n" +
                    "Текущий баланс счёта: %d руб. %02d коп.\n" +
                    "История платежей за последние 3 месяца:\n",
                    account.getId(),
                    account.getName(),
                    account.getBalance() / 100,
                    account.getBalance() % 100
                );
                List<Transfer> history = account.getHistory();
                for(Transfer transfer : history) {
                    if(transfer.getSrc() == null) {
                        System.out.printf(
                            "[%04d] %2$td.%2$tm.%2$tY %2$tH:%2$tM " +
                            "начисление наличными на счёт............. " +
                            "%3$d руб. %4$02d коп.\n",
                            transfer.getId(),
                            transfer.getDate(),
                            transfer.getSumm() / 100,
                            transfer.getSumm() % 100
                        );
                    } else if(transfer.getDest() == null) {
                        System.out.printf(
                            "[%04d] %2$td.%2$tm.%2$tY %2$tH:%2$tM " +
                            "снятие наличными со счёта................ " +
                            "%3$d руб. %4$02d коп.\n",
                            transfer.getId(),
                            transfer.getDate(),
                            transfer.getSumm() / 100,
                            transfer.getSumm() % 100
                        );
                    } else {
                        System.out.printf(
                            "[%04d] %2$td.%2$tm.%2$tY %2$tH:%2$tM " +
                            "перевод со счёта [%3$04d] на счёт [%4$04d]... " +
                            "%5$d руб. %6$02d коп.\n",
                            transfer.getId(),
                            transfer.getDate(),
                            transfer.getSrc().getId(),
                            transfer.getDest().getId(),
                            transfer.getSumm() / 100,
                            transfer.getSumm() % 100
                        );
                    }
                }
            } else {
                System.out.println("Счёта с указанным номером не существует");
            }
        } catch(NumberFormatException e) {
            System.out.println("Номер счёта должен быть целым числом");
        }
        return false;
    }
}
