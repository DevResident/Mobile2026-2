package com.example.intentos;

public class Fibonacci {

    public static String calcularFibonacci(int n) {
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        String result;

        return result = String.valueOf((int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5));
    }
}
