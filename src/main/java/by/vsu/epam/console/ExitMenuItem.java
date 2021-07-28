package by.vsu.epam.console;

import java.util.Scanner;

import by.vsu.epam.service.ServiceException;

public class ExitMenuItem extends MenuItem {
    public ExitMenuItem() {
        super("Выход");
    }

    @Override
    public boolean doWork(Scanner scanner) throws ServiceException {
        System.out.print("Вы действительно хотите выйти? (y/n): ");
        String answer = scanner.nextLine();
        switch(answer.toLowerCase()) {
        case "y":
        case "yes":
        case "д":
        case "да":
            return true;
        }
        return false;
    }
}
