package com.example.actividad4;

public class ViewModel {

    ICalculadora calculadora = new Calculadora();
    private CalculadoraBuilder builder = new CalculadoraBuilder();
    ViewModel(){}

    Double makeOperation(Operation [] operations) {
        Double cache = 0.0;

        for (Operation operation: operations){
            cache += makeOperation(operation);
        }
        return cache;
    }

    //OPeracion simple
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

    //Jerarquia de operacions
    Double makeOperation(CalculadoraInput calculadoraInput){
        Double result = makeOperation(calculadoraInput.operationInitial);
        for (OperationMin operationMin : calculadoraInput.operations){
            Operation operation = new Operation(result, operationMin.getX(), operationMin.getOperationType());
            result = makeOperation(operation);
        }
        return  result;
    }
}
