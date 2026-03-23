package com.example.actividad4;

import java.util.ArrayList;

public class CalculadoraInput {

    Operation operationInitial;
    ArrayList<OperationMin> operations;

    public CalculadoraInput(Operation operationInitial, ArrayList<OperationMin> operations) {
        this.operationInitial = operationInitial;
        this.operations = operations;
    }
}
