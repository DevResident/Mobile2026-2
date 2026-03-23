package com.example.actividad4;

import org.junit.Assert;
import org.junit.Test;

public class CalculadoraBuilderTest {

    @Test
    public void testBuilder(){
        CalculadoraBuilder builder = new CalculadoraBuilder();
        builder.addOperationInitial(
                new Operation(5.0, 5.0, OperationType.ADD)
        );

        builder.addOperation(
                new OperationMin(OperationType.MULTIPLY, 5.0)
        );

        builder.addOperation(
                new OperationMin(OperationType.MINUS, 30.0)
        );

        CalculadoraInput input = builder.build();

        Assert.assertEquals(2, input.operations.size());
        Assert.assertEquals(5.0, input.operations.get(0).getX(), 0.0);
        Assert.assertEquals(OperationType.MULTIPLY, input.operations.get(0).getOperationType());
        Assert.assertEquals(30.0, input.operations.get(1).getX(), 0.0);
        Assert.assertEquals(OperationType.MINUS, input.operations.get(1).getOperationType());
        Assert.assertNotNull(input.operationInitial);
    }
}
