package com.example.actividad4;

import org.junit.Assert;
import org.junit.Test;

public class CalculadoraUnitTest {

    @Test
    public void testOperationAdd(){
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.add(5, 5);
        Assert.assertEquals(10.0, result, 0);
    }

    @Test
    public void testOperationMinus(){
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.minus(5, 5);
        Assert.assertEquals(0, result, 0);
    }

    @Test
    public void testOperationMultiply(){
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.multiply(5, 5);
        Assert.assertEquals(25, result, 0);
    }

    @Test
    public void testOperationDivide(){
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.divide(5, 5);
        Assert.assertEquals(1, result, 0);
    }

    @Test
    public void testOperationDivisionEntrecero() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.divide(5, 0);
        Assert.assertEquals(Double.POSITIVE_INFINITY, result, 0);
    }
}
