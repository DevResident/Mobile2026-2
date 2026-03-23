package com.example.actividad4;

public class OperationMin {
    OperationType operationType;

    Double x;

    public OperationMin(OperationType operationType, Double x) {
        this.operationType = operationType;
        this.x = x;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }
}
