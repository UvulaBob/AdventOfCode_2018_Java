import com.github.javafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class SimulatorHelper {

    static HashMap<Point, Space> generateWorldMap(String fileName) throws IOException{
        HashMap<Point, Space> worldMap = new HashMap<>();
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            Faker faker = new Faker();

            int y = 0;
            for (String line : lines) {
                int x = 0;
                for (char character : line.toCharArray()) {
                    Point point = new Point(x, y);
                    Space space = new Space();
                    switch (character) {
                        case '#': {
                            space.occupant = new Wall();
                            break;
                        }
                        case 'G': {
                            Unit unit = new Unit();
                            unit.name = faker.name().firstName();
                            unit.type = UnitType.GOBLIN;
                            space.occupant = unit;
                            break;
                        }
                        case 'E': {
                            Unit unit = new Unit();
                            unit.name = faker.name().firstName();
                            unit.type = UnitType.ELF;
                            space.occupant = unit;
                            break;
                        }
                    }
                    worldMap.put(point, space);
                    x++;
                }
                y++;
            }

        return worldMap;
    }

    static ArrayList<Point> getOpenAdjacentPoints(ArrayList<Point> points, HashMap<Point, Space> worldMap) {
        ArrayList<Point> openAdjacentPoints = new ArrayList<>();
        for (Point point : points) {
            for (Point openAdjacentPoint : getOpenAdjacentPoints(point, worldMap)) {
                if (!openAdjacentPoints.contains(openAdjacentPoint)) {
                    openAdjacentPoints.add(openAdjacentPoint);
                }
            }
        }
        return openAdjacentPoints;
    }

    private static ArrayList<Point> getOpenAdjacentPoints(Point point, HashMap<Point, Space> worldMap) {
        ArrayList<Point> openAdjacentPoints = getAllAdjacentPoints(point, worldMap);
        for (int i = 0; i < openAdjacentPoints.size(); i++) {
            Point targetPoint = openAdjacentPoints.get(i);
            Space targetSpace = worldMap.get(targetPoint);
            if (targetSpace.occupant != null) {
                openAdjacentPoints.remove(i);
                i--;
            }
        }
        return openAdjacentPoints;
    }

    static Point findClosestTargetPoint(Point origin, ArrayList<Point> targetPoints, HashMap<Point, Space> worldMap) {
        ArrayList<Point> potentialClosestTargetPoints = new ArrayList<>();
        ArrayList<Point> pointsToNeverLookAtAgain = new ArrayList<>();
        pointsToNeverLookAtAgain.add(origin);
        boolean searching = true;
        while (searching) {
            ArrayList<Point> pointsToLookAtThisCycle = getOpenAdjacentPoints(pointsToNeverLookAtAgain, worldMap);
            for (int i = 0; i < pointsToLookAtThisCycle.size(); i++) {
                Point pointToLookAt = pointsToLookAtThisCycle.get(i);
                if (pointsToNeverLookAtAgain.contains(pointToLookAt)) {
                    pointsToLookAtThisCycle.remove(i);
                    i--;
                }
            }

            if (pointsToLookAtThisCycle.size() == 0) {
                return null;
            }

            for (Point pointToLookAt : pointsToLookAtThisCycle) {
                if (targetPoints.contains(pointToLookAt)) {
                    potentialClosestTargetPoints.add(pointToLookAt);
                    searching = false;
                } else {
                    pointsToNeverLookAtAgain.add(pointToLookAt);
                }

            }


        }
        Collections.sort(potentialClosestTargetPoints);
        return potentialClosestTargetPoints.get(0);
    }


        static Point findNextPointOnShortestPath(Point origin, Point targetPoint, HashMap<Point, Space> worldMap) {
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(origin);
        HashSet<Point> visitedPoints = new HashSet<>();
        HashMap<Point, Point> comesFrom = new HashMap<>();
        while (true) {
            Point currentPoint = queue.poll();
            if (currentPoint != null && currentPoint.equals(targetPoint)) {
                break;
            }
            visitedPoints.add(currentPoint);
            ArrayList<Point> adjacentPoints = getOpenAdjacentPoints(currentPoint, worldMap);
            for (Point adjacentPoint : adjacentPoints) {
                if (!visitedPoints.contains(adjacentPoint)) {
                    if (!queue.contains(adjacentPoint)) {
                        queue.add(adjacentPoint);
                    }
                    comesFrom.putIfAbsent(adjacentPoint, currentPoint);
                }
            }
            if (queue.isEmpty()) {
                throw new RuntimeException("This shouldn't happen!");
            }
        }

        LinkedList<Point> path = new LinkedList<>();
        path.add(targetPoint);
        Point currentPoint = targetPoint;
        while (!currentPoint.equals(origin)) {
            path.add(comesFrom.get(currentPoint));
            currentPoint = comesFrom.get(currentPoint);
        }
        path.removeLast();
        return path.peekLast();


        }

    static ArrayList<Point> generateListOfPointsThatContainUnits(HashMap<Point, Space> worldMap) {
        ArrayList<Point> pointsThatContainUnits = new ArrayList<>();
        for (Map.Entry<Point, Space> entry : worldMap.entrySet()) {
            Point currentPoint = entry.getKey();
            Space currentSpace = entry.getValue();
            if (currentSpace.occupant instanceof Unit) {
                pointsThatContainUnits.add(currentPoint);
            }

        }
        return pointsThatContainUnits;
    }

//    static ArrayList<Point> getAllAdjacentPoints(ArrayList<Point> points, HashMap<Point, Space> worldMap) {
//        ArrayList<Point> adjacentPoints = new ArrayList<>();
//        for (Point point : points) {
//            adjacentPoints.addAll(getAllAdjacentPoints(point, worldMap));
//        }
//        return adjacentPoints;
//    }

    private static ArrayList<Point> getAllAdjacentPoints(Point point, HashMap<Point, Space> worldMap) {
        ArrayList<Point> adjacentPoints = new ArrayList<>();
        adjacentPoints.add(new Point(point.x, point.y - 1));
        adjacentPoints.add(new Point(point.x - 1, point.y));
        adjacentPoints.add(new Point(point.x + 1, point.y));
        adjacentPoints.add(new Point(point.x, point.y + 1));
        for (int i = 0; i < adjacentPoints.size(); i++) {
            if (!worldMap.keySet().contains(adjacentPoints.get(i))) {
                adjacentPoints.remove(i);
                i--;
            }
        }
        return adjacentPoints;
    }

    static Point chooseUnitToPunch (ArrayList<Point> pointsThatContainUnits, HashMap<Point, Space> worldMap) {
        Collections.sort(pointsThatContainUnits);
        Point currentTargetPoint = pointsThatContainUnits.get(0);
        Unit currentTargetUnit = (Unit) worldMap.get(currentTargetPoint).occupant;
        for (Point pointThatContainsUnit : pointsThatContainUnits) {
            Unit unit = (Unit) worldMap.get(pointThatContainsUnit).occupant;
            if (unit.hitPoints < currentTargetUnit.hitPoints) {
                currentTargetUnit = unit;
                currentTargetPoint = pointThatContainsUnit;
            }
        }
        return currentTargetPoint;
    }

    static ArrayList<Point> getAdjacentEnemyPoints (Point currentPoint, HashMap<Point, Space> worldMap) {
        Space currentSpace = worldMap.get(currentPoint);
        Occupant occupant = currentSpace.occupant;
        Unit activeUnit;
        if (occupant instanceof Unit) {
            activeUnit = (Unit) occupant;
        } else {
            throw (new RuntimeException("What the fuck?"));
        }

        ArrayList<Point> adjacentEnemyPoints = new ArrayList<>();
        ArrayList<Point> adjacentPoints = SimulatorHelper.getAllAdjacentPoints(currentPoint, worldMap);
        for (Point adjacentPoint : adjacentPoints) {
            Space adjacentSpace = worldMap.get(adjacentPoint);
            if (adjacentSpace.occupant instanceof Unit) {
                Unit adjacentUnit = (Unit) adjacentSpace.occupant;
                if (adjacentUnit.type != activeUnit.type) {
                    adjacentEnemyPoints.add(adjacentPoint);
                }
            }
        }
        return adjacentEnemyPoints;
    }
}

