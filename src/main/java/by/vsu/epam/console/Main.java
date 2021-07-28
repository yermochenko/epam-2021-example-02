package by.vsu.epam.console;

import java.util.Scanner;

import by.vsu.epam.ioc.Context;
import by.vsu.epam.ioc.ContextFactory;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Context context = ContextFactory.newInstance();
        Menu menu = context.getMenu();
        boolean running = true;
        while(running) {
            System.out.println(menu);
            running = !menu.doWork(scanner);
            System.out.println("\nДля продолжения нажмите клавишу Enter");
            scanner.nextLine();
        }
        System.out.println("Всего доброго!");
        scanner.close();
    }
}
