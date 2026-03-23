package com.example.actividad4;

import org.junit.Assert;
import org.junit.Test;

public class ViewModelTest {

    @Test
    public void testMakeOperationAdd(){
        ViewModel viewModel = new ViewModel();
        Operation operation = new Operation(5.0,5.0, OperationType.ADD);

        Double resultado = viewModel.makeOperation(operation);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(10.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationMinus(){
        ViewModel viewModel = new ViewModel();
        Operation operation = new Operation(5.0,5.0, OperationType.MINUS);

        Double resultado = viewModel.makeOperation(operation);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(0.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationMultiply(){
        ViewModel viewModel = new ViewModel();
        Operation operation = new Operation(5.0,5.0, OperationType.MULTIPLY);

        Double resultado = viewModel.makeOperation(operation);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(25.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationDivide(){
        ViewModel viewModel = new ViewModel();
        Operation operation = new Operation(5.0,5.0, OperationType.DIVIDE);

        Double resultado = viewModel.makeOperation(operation);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(1.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationDivEntreCero(){
        ViewModel viewModel = new ViewModel();
        Operation operacion = new Operation(5.0,0.0, OperationType.DIVIDE);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(Double.POSITIVE_INFINITY, resultado, 0.0);
    }

    @Test
    public void testMakeOperations(){
        ViewModel viewModel = new ViewModel();
        Operation operation1 = new Operation(5.0,5.0, OperationType.ADD);
        Operation operation2 = new Operation(5.0,5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(new Operation[]{ operation1, operation2 });
        Assert.assertEquals(20.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationsMix(){
        ViewModel viewModel = new ViewModel();
        Operation operation1 = new Operation(-5.0,-5.0, OperationType.ADD);
        Operation operation2 = new Operation(5.0,5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(new Operation[]{ operation1, operation2 });
        Assert.assertEquals(0.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationsNegative(){
        ViewModel viewModel = new ViewModel();
        Operation operation1 = new Operation(-5.0,-5.0, OperationType.ADD);
        Operation operation2 = new Operation(-5.0,-5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(new Operation[]{ operation1, operation2 });
        Assert.assertEquals(-20.0, resultado, 0.0);
    }
}
