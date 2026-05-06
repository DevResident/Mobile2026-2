package com.example.clase13;

public class Airplane {
    public int id;
    public int x;
    public int y;
    public int landingX;
    public int landingY;
    public Direction direction;
    public boolean collided;

    public Airplane(int id, int x, int y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.landingX = x;
        this.landingY = y;
        this.direction = direction;
        this.collided = false;
    }

    public void setLandingPoint(int landingX, int landingY) {
        this.landingX = landingX;
        this.landingY = landingY;
    }

    public boolean isAtBorder(int gridSize) {
        return x == 0 || y == 0 || x == gridSize - 1 || y == gridSize - 1;
    }

    // Constructor de copia profunda
    public Airplane(Airplane other) {
        this.id = other.id;
        this.x = other.x;
        this.y = other.y;
        this.landingX = other.landingX;
        this.landingY = other.landingY;
        this.direction = other.direction;
        this.collided = other.collided;
    }
}
