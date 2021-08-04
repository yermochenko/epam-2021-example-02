package by.vsu.epam.console;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.vsu.epam.service.ServiceException;

public class Menu {
    private static final Logger logger = LogManager.getLogger();
    private Map<Integer, MenuItem> items = new LinkedHashMap<>();

    public void add(int number, MenuItem item) {
        items.put(number, item);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Меню:\n");
        for(Map.Entry<Integer, MenuItem> entry : items.entrySet()) {
            builder
                .append("    ")
                .append(entry.getKey())
                .append(") ")
                .append(entry.getValue().getName())
                .append(".\n");
        }
        return builder.toString();
    }

    public boolean doWork(Scanner scanner) {
        System.out.print("> ");
        try {
            Integer menuItemNumber = Integer.valueOf(scanner.nextLine());
            MenuItem item = items.get(menuItemNumber);
            if(item == null) {
                throw new IllegalArgumentException();
            }
            return item.doWork(scanner);
        } catch(NumberFormatException e) {
            System.out.println("Неверный номер пункта меню. Необходимо ввести целое число - номер пункта меню.");
        } catch(IllegalArgumentException e) {
            System.out.println("Неверный номер пункта меню. Необходимо ввести номер существующего пункта меню.");
        } catch(ServiceException e) {
            logger.error("Ошибка обработки данных", e);
            System.out.println("Ошибка обработки данных");
        }
        return false;
    }
}
