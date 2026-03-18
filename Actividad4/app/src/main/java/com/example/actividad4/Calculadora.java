package com.example.actividad4;

public class Calculadora implements ICalculadora {

    @Override
    public Double add(double x, double y) {
        return x + y;
    }

    @Override
    public Double minus(double x, double y) {
        return x - y;
    }

    @Override
    public Double multiply(double x, double y) {
        return x * y;
    }

    @Override
    public Double divide(double x, double y) {
        return x / y;
    }
}
