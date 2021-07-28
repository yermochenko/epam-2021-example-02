package by.vsu.epam.console;

import java.util.Scanner;

import by.vsu.epam.service.ServiceException;

public abstract class MenuItem {
    private String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean doWork(Scanner scanner) throws ServiceException;
}
