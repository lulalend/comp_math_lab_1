package ru.itmo.math.handlers;

import ru.itmo.math.methods.IterMethod;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReader {

    private final Scanner scanner;
    private final IterMethod method;

    public ConsoleReader(Scanner scanner, IterMethod method) {
        this.scanner = scanner;
        this.method = method;
    }

    public void readFromConsole() {
        int size = 0;

        while (true) {
            System.out.println("Укажите размерносить матрицы: ");
            String line = scanner.next();
            if (!isNumber(line)) {
                System.out.println("Введите число!");
            } else if (Integer.parseInt(line) <= 1) {
                System.out.println("Размерность не может быть меньше 2");
            } else {
                size = Integer.parseInt(line);
                break;
            }
        }

        System.out.println(size);
        double[][] a = new double[size][size];
        double[] b = new double[size];
        boolean flag = false;

        System.out.println("Введите коффициенты через пробел:");

        while (!flag) {
            try {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        a[i][j] = Double.parseDouble(scanner.next());
                    }
                    b[i] = Double.parseDouble(scanner.next());
                }
                flag = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Дробные числа записывать через точку... Заново!");
            }
        }

        flag = false;
        double eps = 0;

        while (!flag) {
            try {
                System.out.println("Укажите eps (разделитель - точка): ");
                eps = Double.parseDouble(scanner.next());
                flag = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Дробные числа записывать через точку!");
            }
        }

        method.solution(size, eps, a, b);
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
