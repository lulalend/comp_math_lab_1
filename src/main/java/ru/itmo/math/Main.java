package ru.itmo.math;

import ru.itmo.math.handlers.ConsoleReader;
import ru.itmo.math.handlers.FileReader;
import ru.itmo.math.methods.GaussSeidelMethod;
import ru.itmo.math.methods.IterMethod;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IterMethod method = new GaussSeidelMethod();
        while (true) {
            System.out.println("Будем считывать данные с консоли(1) или файла(2)?");
            String symbol = scanner.next();
            try {
                if ("1".equals(symbol)) {
                    new ConsoleReader(scanner, method).readFromConsole();
                    break;
                } else if ("2".equals(symbol)) {
                    new FileReader(method).readFromFile();
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println("На главной диагонали 0 - плохо дело!");
                System.exit(0);
            }


            System.out.println("Необходимо ввести 1 или 2!");
        }

        scanner.close();
    }
}