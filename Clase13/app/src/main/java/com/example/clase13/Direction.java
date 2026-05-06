package com.example.clase13;

public enum Direction {
    N(0, -1),
    S(0, 1),
    E(1, 0),
    W(-1, 0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Ángulo de rotación del icono ✈ (que naturalmente apunta a la derecha)
    public float rotationDeg() {
        switch (this) {
            case N: return -90f;
            case S: return  90f;
            case E: return   0f;
            case W: return 180f;
            default: return  0f;
        }
    }

}
