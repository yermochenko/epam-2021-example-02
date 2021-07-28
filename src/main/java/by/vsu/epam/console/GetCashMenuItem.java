package by.vsu.epam.console;

import java.util.Scanner;

import by.vsu.epam.service.AccountNotExistsException;
import by.vsu.epam.service.NotEnoughMoneyExistsException;
import by.vsu.epam.service.ServiceException;
import by.vsu.epam.service.TransferService;

public class GetCashMenuItem extends MenuItem {
    private TransferService transferService;

    public GetCashMenuItem() {
        super("Снять со счёта наличные");
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
            transferService.getCash(accountId, summ);
            System.out.println("Списание успешно выполнено");
        } catch(NumberFormatException e) {
            System.out.println("Номер счёта должен быть целым числом");
        } catch(AccountNotExistsException e) {
            System.out.println("Счёта с указанным номером не существует");
        } catch(NotEnoughMoneyExistsException e) {
            System.out.println("На счету недостаточно средств для осуществления операции");
        }
        return false;
    }
}
