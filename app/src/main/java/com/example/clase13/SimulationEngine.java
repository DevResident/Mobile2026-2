package com.example.clase13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class SimulationEngine {

    private static final int MIN_GRID_SIZE = 8;
    private static final int MAX_GRID_SIZE = 20;
    private static final int MIN_AIRPLANES = 8;
    private static final int MAX_AIRPLANES = 100;

    private final int gridSize;
    private final int airplaneCount;
    private final ArrayList<SimulationState> history = new ArrayList<>();
    private int currentIndex = 0;

    public SimulationEngine() {
        Random random = new Random();
        this.gridSize = randomInRange(random, MIN_GRID_SIZE, MAX_GRID_SIZE);
        this.airplaneCount = randomAirplaneCount(random, gridSize);
        history.add(buildInitialState(random));
    }

    public SimulationEngine(int gridSize) {
        this.gridSize = gridSize;
        Random random = new Random();
        this.airplaneCount = randomAirplaneCount(random, gridSize);
        history.add(buildInitialState(random));
    }

    // Genera aviones en posiciones únicas aleatorias con dirección aleatoria
    private SimulationState buildInitialState(Random random) {
        ArrayList<Airplane> planes = new ArrayList<>();
        HashSet<String> occupied = new HashSet<>();
        Direction[] directions = Direction.values();

        addPlannedLandingPairs(random, planes, occupied);

        while (planes.size() < airplaneCount) {
            int x = randomInRange(random, 1, gridSize - 2);
            int y = randomInRange(random, 1, gridSize - 2);
            String key = x + "," + y;
            if (!occupied.contains(key)) {
                occupied.add(key);
                Direction dir = directions[random.nextInt(directions.length)];
                Airplane plane = new Airplane(planes.size() + 1, x, y, dir);
                CollisionPoint landing = randomLandingOnPath(random, x, y, dir);
                plane.setLandingPoint(landing.x, landing.y);
                planes.add(plane);
            }
        }
        return new SimulationState(
                0,
                0,
                planes
        );
    }

    private void addPlannedLandingPairs(
            Random random,
            ArrayList<Airplane> planes,
            HashSet<String> occupied
    ) {
        int pairCount = Math.max(1, airplaneCount / 6);
        int attempts = 0;

        while (planes.size() + 1 < airplaneCount && pairCount > 0 && attempts < 300) {
            attempts++;
            boolean horizontal = random.nextBoolean();
            int landingX = randomInRange(random, 2, gridSize - 3);
            int landingY = randomInRange(random, 2, gridSize - 3);

            if (horizontal) {
                int maxDistance = Math.min(landingX - 1, gridSize - 2 - landingX);
                if (maxDistance < 1) continue;

                int distance = randomInRange(random, 1, maxDistance);
                if (addLandingPair(
                        planes,
                        occupied,
                        landingX - distance,
                        landingY,
                        Direction.E,
                        landingX + distance,
                        landingY,
                        Direction.W,
                        landingX,
                        landingY
                )) {
                    pairCount--;
                }
            } else {
                int maxDistance = Math.min(landingY - 1, gridSize - 2 - landingY);
                if (maxDistance < 1) continue;

                int distance = randomInRange(random, 1, maxDistance);
                if (addLandingPair(
                        planes,
                        occupied,
                        landingX,
                        landingY - distance,
                        Direction.S,
                        landingX,
                        landingY + distance,
                        Direction.N,
                        landingX,
                        landingY
                )) {
                    pairCount--;
                }
            }
        }
    }

    private boolean addLandingPair(
            ArrayList<Airplane> planes,
            HashSet<String> occupied,
            int firstX,
            int firstY,
            Direction firstDirection,
            int secondX,
            int secondY,
            Direction secondDirection,
            int landingX,
            int landingY
    ) {
        String firstKey = firstX + "," + firstY;
        String secondKey = secondX + "," + secondY;
        if (occupied.contains(firstKey) || occupied.contains(secondKey)) return false;

        addPlane(planes, occupied, firstX, firstY, firstDirection, landingX, landingY);
        addPlane(planes, occupied, secondX, secondY, secondDirection, landingX, landingY);
        return true;
    }

    private boolean addPlane(
            ArrayList<Airplane> planes,
            HashSet<String> occupied,
            int x,
            int y,
            Direction direction,
            int landingX,
            int landingY
    ) {
        String key = x + "," + y;
        if (occupied.contains(key)) return false;

        occupied.add(key);
        Airplane plane = new Airplane(planes.size() + 1, x, y, direction);
        plane.setLandingPoint(landingX, landingY);
        planes.add(plane);
        return true;
    }

    private CollisionPoint randomLandingOnPath(Random random, int x, int y, Direction direction) {
        switch (direction) {
            case N:
                return new CollisionPoint(x, randomInRange(random, 0, y - 1));
            case S:
                return new CollisionPoint(x, randomInRange(random, y + 1, gridSize - 1));
            case E:
                return new CollisionPoint(randomInRange(random, x + 1, gridSize - 1), y);
            case W:
                return new CollisionPoint(randomInRange(random, 0, x - 1), y);
            default:
                return new CollisionPoint(x, y);
        }
    }

    private static int randomAirplaneCount(Random random, int gridSize) {
        int capacity = (gridSize - 2) * (gridSize - 2);
        int max = Math.min(MAX_AIRPLANES, capacity);
        int min = Math.min(MIN_AIRPLANES, max);
        return randomInRange(random, min, max);
    }

    private static int randomInRange(Random random, int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public SimulationState currentState() {
        return history.get(currentIndex);
    }

    public boolean canGoPrev() {
        return currentIndex > 0;
    }

    public void nextStep() {
        if (currentIndex + 1 < history.size()) {
            currentIndex++;
            return;
        }

        SimulationState prev = currentState();
        ArrayList<Airplane> nextPlanes = new ArrayList<>();

        // 1. Mover cada avión no colisionado una celda en su dirección
        for (Airplane a : prev.airplanes) {
            Airplane moved = new Airplane(a);
            if (!moved.collided) {
                moved.x += moved.direction.dx;
                moved.y += moved.direction.dy;
            }
            nextPlanes.add(moved);
        }

        ArrayList<CollisionPoint> collisionPoints = markCurrentLandingCollisions(nextPlanes);

        ArrayList<Airplane> visiblePlanes = new ArrayList<>();
        for (Airplane plane : nextPlanes) {
            if (!plane.isAtBorder(gridSize) && !plane.collided) {
                visiblePlanes.add(plane);
            }
        }

        history.add(new SimulationState(
                prev.step + 1,
                prev.collisions + collisionPoints.size(),
                visiblePlanes,
                collisionPoints
        ));
        currentIndex++;
    }

    private static ArrayList<CollisionPoint> markCurrentLandingCollisions(ArrayList<Airplane> planes) {
        ArrayList<CollisionPoint> points = new ArrayList<>();
        for (ArrayList<Airplane> group : buildCurrentLandingMap(planes).values()) {
            if (group.size() > 1) {
                Airplane first = group.get(0);
                points.add(new CollisionPoint(first.landingX, first.landingY));
                for (Airplane plane : group) {
                    plane.collided = true;
                }
            }
        }
        return points;
    }

    private static HashMap<String, ArrayList<Airplane>> buildCurrentLandingMap(ArrayList<Airplane> planes) {
        HashMap<String, ArrayList<Airplane>> landingMap = new HashMap<>();
        for (Airplane plane : planes) {
            if (plane.collided || plane.x != plane.landingX || plane.y != plane.landingY) continue;
            String key = plane.landingX + "," + plane.landingY;
            if (!landingMap.containsKey(key)) landingMap.put(key, new ArrayList<>());
            landingMap.get(key).add(plane);
        }
        return landingMap;
    }

    public void prevStep() {
        if (canGoPrev()) currentIndex--;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getAirplaneCount() {
        return airplaneCount;
    }
}
