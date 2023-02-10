package ru.itmo.math.handlers;

import ru.itmo.math.methods.IterMethod;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private final IterMethod method;

    public FileReader(IterMethod method) {
        this.method = method;
    }

    public void readFromFile() {
        try {
            String file = "test1.txt";
            Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));
            String data;
            double eps = -1;
            int size = 0;
            int columns = 0;
            List<double[]> list = new ArrayList<>();

            while (scanner.hasNext()) {
                data = scanner.nextLine();
                String[] tmp = data.split(" ");
                if (eps == -1) {
                    eps = Double.parseDouble(tmp[0]);
                } else {
                    columns = tmp.length;
                    double[] array = new double[columns];
                    for (int i = 0; i < columns; i++) {
                        array[i] = Double.parseDouble(tmp[i]);
                    }
                    list.add(array);
                    size++;
                }
            }

            if ((size + 1) != columns) {
                System.out.println("Матрица не квадратная (");
                System.exit(0);
            }

            double[][] a = new double[size][size];
            double[] b = new double[size];

            for (int i = 0; i < size; i++) {
                System.arraycopy(list.get(i), 0, a[i], 0, size);
                b[i] = list.get(i)[size];
            }

            method.solution(size, eps, a, b);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка!  Проверьте, что дробные числа записаны через точку");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Проверьте путь до файла");
            System.exit(0);
        }


    }
}
