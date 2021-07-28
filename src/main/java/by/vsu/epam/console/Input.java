package by.vsu.epam.console;

import java.util.Scanner;

public class Input {
    public static Long summ(Scanner scanner) {
        String summ[] = scanner.nextLine().split("\\s+");
        if(summ.length != 2) {
            System.out.println("Сумма должна состоять из двух чисел, разделённых пробелом");
            return null;
        }
        Long rubles = null;
        try {
            rubles = Long.valueOf(summ[0]);
        } catch(NumberFormatException e) {
            System.out.println("Количество рублей должно быть целым числом");
            return null;
        }
        if(rubles <= 0) {
            System.out.println("Количество рублей должно быть положительным");
            return null;
        }
        Long kopecks = null;
        try {
            kopecks = Long.valueOf(summ[1]);
        } catch(NumberFormatException e) {
            System.out.println("Количество копеек должно быть целым числом");
            return null;
        }
        if(kopecks < 0) {
            System.out.println("Количество копеек должно быть неотрицательным числом");
            return null;
        }
        if(kopecks >= 100) {
            System.out.println("Количество копеек должно быть меньше 100");
            return null;
        }
        return rubles * 100 + kopecks;
    }
}
