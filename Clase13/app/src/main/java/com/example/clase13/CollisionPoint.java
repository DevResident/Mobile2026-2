package com.example.clase13;

public class CollisionPoint {
    public int x;
    public int y;

    public CollisionPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CollisionPoint(CollisionPoint other) {
        this.x = other.x;
        this.y = other.y;
    }
}
