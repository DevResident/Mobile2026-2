package com.example.actividad4;

import java.util.ArrayList;

public class CalculadoraBuilder {

    private Operation operationInitial = null;
    private ArrayList<OperationMin> operations = new ArrayList<>();

    public CalculadoraBuilder(){
    }

    public CalculadoraBuilder addOperationInitial(Operation operationInitial){
        this.operationInitial = operationInitial;
        return this;
    }

    public CalculadoraBuilder addOperation(OperationMin operation){
        operations.add(operation);
        return this;
    }

    public CalculadoraInput build(){
        return new CalculadoraInput(operationInitial, operations);
    }
}
