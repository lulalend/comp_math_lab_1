package ru.itmo.math.methods;

import ru.itmo.math.exceptions.DiagonalPriorityException;

public class GaussSeidelMethod implements IterMethod {

    private final DiagonalPriorityHandler handler;

    public GaussSeidelMethod() {
        this.handler = new DiagonalPriorityHandler();
    }

    public double[][] solution(int n, double eps, double[][] a, double[] b) {
        int k = 0;
        double[][] newA;

//        попытка получить матрицу с диагональным преобладанием
        try {
            if (!this.handler.isDiagonalPriority(a, n)) {
                newA = this.handler.doDiagonalPriority(n, a, b);
                for (int i = 0; i < n; i++) {
                    System.arraycopy(newA[i], 0, a[i], 0, n);
                    b[i] = newA[i][n];
                }
            }
        } catch (DiagonalPriorityException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        double[][] c = new double[n][n];
        double[] d = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    c[i][j] = 0;
                } else {
                    c[i][j] = -a[i][j] / a[i][i];
                }
            }
            d[i] = b[i] / a[i][i];
        }

        int maxIterate = 10;
        int currencyIterate = 0;
        double[] data = new double[n];
        double[] ans = new double[n];
        System.arraycopy(d, 0, ans, 0, n);
        double[] deviation = new double[n];
        double maxDev = -100;

        System.out.printf("%n%3d | ", currencyIterate);
        for (int i = 0; i < n; i++) {
            System.out.printf("%7.4f | ", ans[i]);
        }
        System.out.printf("%4s    | %n", '-');

        while (k != n && currencyIterate <= maxIterate) {
            currencyIterate++;
            maxDev = -100;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    data[i] += c[i][j] * ans[j];
                }
                data[i] += d[i];
                data[i] = round(data[i]);
                deviation[i] = inaccuracy(data[i], ans[i]);
                maxDev = Math.max(maxDev, deviation[i]);
                ans[i] = data[i];
                data[i] = 0;
            }

            System.out.printf("%3d | ", currencyIterate);
            for (int i = 0; i < n; i++) {
                System.out.printf("%7.4f | ", ans[i]);
            }
            System.out.printf("%7.4f | %n", maxDev);
//            проверяем погрешность
            k = 0;
            for (int i = 0; i < n; i++) {
                k += deviation[i] < eps ? 1 : 0;
            }
        }
        return a;
    }

    private double inaccuracy(double x1, double x0) {
        return Math.abs(x1 - x0);
    }

    private double round(double a) {
        return (double) Math.round(a * 10000d) / 10000d;
    }
}
