package com.example.actividad4;

public class ViewModel {

    ICalculadora calculadora = new Calculadora();
    private CalculadoraBuilder builder = new CalculadoraBuilder();
    ViewModel(){

    }

    Double makeOperation(Operation [] operations) {
        Double cache = 0.0;

        for (Operation operation: operations){
            cache += makeOperation(operation);
        }
        return cache;
    }

    Double makeOperation(Operation operation){
        switch (operation.getType()){
            case ADD:
                return calculadora.add(operation.x, operation.y);
            case MINUS:
                return calculadora.minus(operation.x, operation.y);
            case MULTIPLY:
                return calculadora.multiply(operation.x, operation.y);
            case DIVIDE:
                return calculadora.divide(operation.x, operation.y);
            default:
                return 0.0;
        }
    }
}
