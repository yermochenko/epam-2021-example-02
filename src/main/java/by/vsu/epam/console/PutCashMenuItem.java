package by.vsu.epam.console;

import java.util.Scanner;

import by.vsu.epam.service.AccountNotExistsException;
import by.vsu.epam.service.ServiceException;
import by.vsu.epam.service.TransferService;

public class PutCashMenuItem extends MenuItem {
    private TransferService transferService;

    public PutCashMenuItem() {
        super("Пополнить счёт наличными");
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    @Override
    public boolean doWork(Scanner scanner) throws ServiceException {
        try {
            System.out.print("Введите номер счёта: ");
            Long accountId = Long.valueOf(scanner.nextLine());
            System.out.print("Введите сумму (рубли и копейки разделяйте пробелом): ");
            Long summ = Input.summ(scanner);
            if(summ == null) {
                return false;
            }
            transferService.putCash(accountId, summ);
            System.out.println("Пополнение успешно выполнено");
        } catch(NumberFormatException e) {
            System.out.println("Номер счёта должен быть целым числом");
        } catch(AccountNotExistsException e) {
            System.out.println("Счёта с указанным номером не существует");
        }
        return false;
    }
}
