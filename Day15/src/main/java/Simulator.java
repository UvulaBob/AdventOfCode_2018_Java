import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Simulator {

    private static Simulator single_instance = null;
    private HashMap<Point, Space> worldMap = new HashMap<>();
    private ArrayList<Point> pointsThatContainUnits;
    private static int cycleCount = 0;
    boolean part2 = false;


    static Simulator getInstance() {
        if (single_instance == null) {
            single_instance = new Simulator();
        }

        return single_instance;
    }

    private Simulator() {
    }

    void reset() {
        worldMap = new HashMap<>();
        pointsThatContainUnits = null;
        cycleCount = 0;
    }

    void generateWorldMap(String fileName) throws IOException {
        worldMap = SimulatorHelper.generateWorldMap(fileName);
    }

    private void doOneCycle () {
        for (int i = 0 ; i < pointsThatContainUnits.size(); i++) {
            Point activePoint = pointsThatContainUnits.get(i);
            ArrayList<Point> adjacentEnemyPoints = SimulatorHelper.getAdjacentEnemyPoints(activePoint, worldMap);
            Unit activeUnit = (Unit) worldMap.get(activePoint).occupant;
            if (activeUnit.actedThisTurn) {
                System.out.println(String.format("%s has already acted this cycle. Skipping.", activeUnit.name));
                continue;
            }

            if (adjacentEnemyPoints.size() == 0) {
                ArrayList<Point> pointsWithEnemyUnits = new ArrayList<>();
                for (Point pointThatContainsUnit : pointsThatContainUnits) {
                    Unit unit = (Unit) worldMap.get(pointThatContainsUnit).occupant;
                    if (unit.type != activeUnit.type) {
                        pointsWithEnemyUnits.add(pointThatContainsUnit);
                    }
                }

                if (pointsWithEnemyUnits.size() == 0) {
                    cycleCount--;
                    return;
                }

                ArrayList<Point> openTargetPoints = SimulatorHelper.getOpenAdjacentPoints(pointsWithEnemyUnits, worldMap);
                if (openTargetPoints.size() > 0) {
                    Point closestTargetPoint = SimulatorHelper.findClosestTargetPoint(activePoint, openTargetPoints, worldMap);
                    if (closestTargetPoint != null) {
                        Point destinationPoint = SimulatorHelper.findNextPointOnShortestPath(activePoint, closestTargetPoint, worldMap);
                        Space activeSpace = worldMap.get(activePoint);
                        activeSpace.occupant = null;
                        Space destinationSpace = worldMap.get(destinationPoint);
                        destinationSpace.occupant = activeUnit;
                        pointsThatContainUnits.set(i, destinationPoint);
                        System.out.println(String.format("%s moved from (%s) to (%s)", activeUnit.name, activePoint, destinationPoint));
                        activePoint = destinationPoint;
                    }
                    else {
                        System.out.println(String.format("%s couldn't reach an open spot adjacent to an enemy. Not moving from %s", activeUnit.name, activePoint));
                    }
                }
            }

            adjacentEnemyPoints = SimulatorHelper.getAdjacentEnemyPoints(activePoint, worldMap);
            if (adjacentEnemyPoints.size() > 0) {
                Point pointWithEnemyToPunch = SimulatorHelper.chooseUnitToPunch(adjacentEnemyPoints, worldMap);
                Unit enemyToPunch = (Unit) worldMap.get(pointWithEnemyToPunch).occupant;
                enemyToPunch.hitPoints -= activeUnit.attackPower;
                System.out.print(String.format("%s (%s) punched %s (%s). ", activeUnit.name, activePoint, enemyToPunch.name, pointWithEnemyToPunch));
                System.out.println(String.format("%s now has %s hit points", enemyToPunch.name, enemyToPunch.hitPoints));
                if (enemyToPunch.hitPoints <= 0) {
                    if (part2 && enemyToPunch.type == UnitType.ELF) {
                        throw new RuntimeException("Elf down! Start over!");
                    }
                    worldMap.get(pointWithEnemyToPunch).occupant = null;
                    pointsThatContainUnits.remove(pointWithEnemyToPunch);
                    i--;
                    System.out.println(String.format("%s is now dead.", enemyToPunch.name));
                }
            }
            activeUnit.actedThisTurn = true;
        }
        System.out.println("Everyone has taken their turn.");
    }
    void runSimulation() {
        pointsThatContainUnits = SimulatorHelper.generateListOfPointsThatContainUnits(worldMap);
        Collections.sort(pointsThatContainUnits);

        while (bothSidesAlive()) {
            cycleCount++;
            pointsThatContainUnits = SimulatorHelper.generateListOfPointsThatContainUnits(worldMap);
            Collections.sort(pointsThatContainUnits);
            for (Point point : pointsThatContainUnits) {
                Unit unit = (Unit) worldMap.get(point).occupant;
                unit.actedThisTurn = false;
            }
            System.out.println(String.format("Start of Round %s", cycleCount));
            doOneCycle();
            System.out.println(String.format("End of Round %s", cycleCount));
        }


        int totalHitPoints = 0;
        String winningUnitType = "";
        for (Point point : pointsThatContainUnits) {
            Unit unit = (Unit) worldMap.get(point).occupant;
            totalHitPoints += unit.hitPoints;
            System.out.println(String.format("%s: %s hit points left", unit.name, unit.hitPoints));
            winningUnitType = unit.type.toString();
        }
        System.out.println(String.format("%ss win!", winningUnitType));
        System.out.println(String.format("%s rounds completed", cycleCount));
        System.out.println(String.format("%s total hit points left", totalHitPoints));
        System.out.println(String.format("Final Answer: %s", totalHitPoints * cycleCount));
    }

    private boolean bothSidesAlive() {
        boolean atLeastOneElfAlive = false;
        boolean atLeastOneGoblinAlive = false;

        for (Point point : pointsThatContainUnits) {
            Unit unit = (Unit) worldMap.get(point).occupant;
            if (unit.type == UnitType.ELF) {
                atLeastOneElfAlive = true;
            } else {
                atLeastOneGoblinAlive = true;
            }
        }

        return (atLeastOneElfAlive && atLeastOneGoblinAlive);
    }


    public void powerUpElves(int elfPower) {
        for (Point point : worldMap.keySet()) {
            Space space = worldMap.get(point);
            if (space.occupant != null) {
                if (space.occupant instanceof Unit) {
                    Unit unit = (Unit) worldMap.get(point).occupant;
                    if (unit.type == UnitType.ELF) {
                        unit.attackPower = elfPower;
                    }
                }
            }
        }

    }
}
