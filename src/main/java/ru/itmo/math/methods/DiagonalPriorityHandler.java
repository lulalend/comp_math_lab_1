package ru.itmo.math.methods;

import ru.itmo.math.exceptions.DiagonalPriorityException;

import java.util.*;

public class DiagonalPriorityHandler {
    // проверяем диагональное преобладание
    public boolean isDiagonalPriority(double[][] a, int n) {
        boolean flag = true;

        for (int i = 0; i < n; i++) {
            double s = 0;
            for (int j = 0; j < n; j++) {
                s += Math.abs(a[i][j]);
            }
            s -= Math.abs(a[i][i]);
            if (s > Math.abs(a[i][i])) {
                flag = false;
            }
        }

        return flag;
    }

    //    пытаемся сделать диагональное преобладание
    public double[][] doDiagonalPriority(int n, double[][] a, double[] b) {
        List<SortedSet<Integer>> options = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            options.add(new TreeSet<>());
        }

        int k = 0;
        for (int i = 0; i < n; i++) {
            double s = 0;
            for (int j = 0; j < n; j++) {
                s += Math.abs(a[i][j]);
            }
            for (int j = 0; j < n; j++) {
                if (Math.abs(a[i][j]) >= (s - Math.abs(a[i][j]))) {
                    SortedSet<Integer> set = options.get(j);
                    set.add(i);
                }
                if (Math.abs(a[i][j]) > (s - Math.abs(a[i][j]))) {
                    k++;
                }
            }
        }

        if (k == 0) {
            throw new DiagonalPriorityException("Невозможно преобразовать к диагональному виду ;(");
        }
        checkPotentialDiagonalPriority(options, n);

        int maxLen = 20;
        while (maxLen > 2) {
            int curMaxLen = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && options.get(j).size() == 1) {
                        options.get(i).removeAll(options.get(j));
                        curMaxLen = Math.max(curMaxLen, options.get(i).size());
                    }
                }
            }
            maxLen = curMaxLen;
        }

        checkPotentialDiagonalPriority(options, n);

//        получаем необходимую последовательность строк
        for (int i = 0; i < n; i++) {
            if (options.get(i).size() == 2) {
                options.get(i).remove(options.get(i).last());
            }
            for (int j = i + 1; j < n; j++) {
                options.get(j).removeAll(options.get(i));
            }
        }

        checkPotentialDiagonalPriority(options, n);

        double[][] newArray = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newArray[i][j] = a[options.get(i).first()][j];
            }
            newArray[i][n] = b[options.get(i).first()];
        }

        return newArray;
    }

    private void checkPotentialDiagonalPriority(List<SortedSet<Integer>> options, int n) {
        for (int i = 0; i < n; i++) {
            if (options.get(i).equals(Collections.emptySet())) {
                throw new DiagonalPriorityException("Невозможно преобразовать к диагональному виду :(");
            }
        }
    }
}
