package com.example.clase13;

import java.util.ArrayList;

public class SimulationState {
    public int step;
    public int collisions;
    public ArrayList<Airplane> airplanes;
    public ArrayList<CollisionPoint> collisionPoints;

    public SimulationState(int step, int collisions, ArrayList<Airplane> airplanes) {
        this(step, collisions, airplanes, new ArrayList<>());
    }

    public SimulationState(
            int step,
            int collisions,
            ArrayList<Airplane> airplanes,
            ArrayList<CollisionPoint> collisionPoints
    ) {
        this.step = step;
        this.collisions = collisions;
        this.airplanes = deepCopy(airplanes);
        this.collisionPoints = deepCopyCollisionPoints(collisionPoints);
    }

    private static ArrayList<Airplane> deepCopy(ArrayList<Airplane> source) {
        ArrayList<Airplane> copy = new ArrayList<>();
        for (Airplane a : source) {
            copy.add(new Airplane(a));
        }
        return copy;
    }

    private static ArrayList<CollisionPoint> deepCopyCollisionPoints(ArrayList<CollisionPoint> source) {
        ArrayList<CollisionPoint> copy = new ArrayList<>();
        for (CollisionPoint point : source) {
            copy.add(new CollisionPoint(point));
        }
        return copy;
    }
}
