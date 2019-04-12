import java.util.*;

public class Part2 {
    private static final int depth = 4845;
    private static final int targetX = 6;
    private static final int targetY = 770;
    private static int searchAreaX = targetX * 9;
    private static int searchAreaY = targetY * 9;
    private static final int originX = 0;
    private static final int originY = 0;

    private static HashMap<String, Integer> erosionLevels = new HashMap<>();
    private static HashSet<String> visited = new HashSet<>();
    private static HashMap<String, Integer> distances = new HashMap<>();

    public static void main(String[] args) {
        for (int y = originY; y <= searchAreaY; y++) {
            for (int x = originX; x <= searchAreaX; x++) {
                erosionLevels.put(formattedPoint(x, y), calculateErosionLevel(x, y));
            }
        }

        // If I use a Priority Queue, it works. For some reason, when using a FIFO queue, it's not following a
        // specific path that would (presumably) result in the shortest path. Both methods clear the whole queue
        // before finishing, so I don't know why one works and the other doesn't.

        Queue<String> queue = new PriorityQueue<>(new CompareUsingDistanceHashMap(distances));

        //        Queue<String> queue = new ArrayDeque<>();
        queue.add(formattedPoint(originX, originY, "T"));
        distances.put(formattedPoint(originX, originY, "T"), 0);

        while (!queue.isEmpty()) {
            String currentPointWithTool = queue.poll();

            if (visited.contains(currentPointWithTool)) {
                continue;
            }

            visited.add(currentPointWithTool);
            int currentDistance = distances.get(currentPointWithTool);
            String[] splitCurrentPointWithTool = currentPointWithTool.split(",");
            int x = Integer.parseInt(splitCurrentPointWithTool[0]);
            int y = Integer.parseInt(splitCurrentPointWithTool[1]);
            String currentTool = splitCurrentPointWithTool[2];

            for (Point adjacentPoint : getAdjacentPoints(x, y)) {
                String formattedAdjacentPoint =  formattedPoint(adjacentPoint.x, adjacentPoint.y, currentTool);
                ArrayList<String> validToolsAtAdjacentPoint = validToolsAtPoint(adjacentPoint.x, adjacentPoint.y);
                if (validToolsAtAdjacentPoint.contains(currentTool)) {
                    distances.putIfAbsent(formattedAdjacentPoint, currentDistance + 1);
                    if (currentDistance + 1 < distances.get(formattedAdjacentPoint)) {
                        distances.put(formattedAdjacentPoint, currentDistance + 1);
                    }
                    queue.add(formattedAdjacentPoint);
                }
            }

            ArrayList<String> validToolsAtCurrentPoint = validToolsAtPoint(x, y);
            validToolsAtCurrentPoint.remove(currentTool);
            String otherValidTool = validToolsAtCurrentPoint.get(0);
            String formattedCurrentPointWithOtherValidTool = formattedPoint(x, y, otherValidTool);

            distances.putIfAbsent(formattedCurrentPointWithOtherValidTool, currentDistance + 7);
            if (currentDistance + 7 < distances.get(formattedCurrentPointWithOtherValidTool)) {
                distances.put(formattedCurrentPointWithOtherValidTool, currentDistance + 7);
            }
            queue.add(formattedCurrentPointWithOtherValidTool);
        }

        System.out.println("Shortest Distance to Target: " + distances.get(formattedPoint(targetX, targetY, "T")));
        System.out.println("Done!");

    }


    private static ArrayList<Point> getAdjacentPoints(int x, int y) {
        ArrayList<Point> adjacentPoints = new ArrayList<>();

        if (x < searchAreaX) {
            adjacentPoints.add(new Point(x + 1, y));
        }

        if (y < searchAreaY) {
            adjacentPoints.add(new Point(x, y + 1));
        }

        if (x > 0) {
            adjacentPoints.add(new Point(x - 1, y));
        }

        if (y > 0) {
            adjacentPoints.add(new Point(x, y - 1));
        }

        return adjacentPoints;

    }

    private static ArrayList<String> validToolsAtPoint(int x, int y) {
        ArrayList<String> validTools = new ArrayList<>();
        String point = formattedPoint(x, y);

        switch (erosionLevels.get(point) % 3) {
            case 0:
                validTools.addAll(Arrays.asList("C", "T"));
                break;
            case 1:
                validTools.addAll(Arrays.asList("C", "N"));
                break;
            case 2:
                validTools.addAll(Arrays.asList("N", "T"));
                break;
        }

        return validTools;

    }

    private static String formattedPoint(int x, int y) {
        return String.format("%s,%s", x, y);
    }

    private static String formattedPoint(int x, int y, String tool) {
        return String.format("%s,%s,%s", x, y, tool);
    }

    private static int calculateErosionLevel(int x, int y) {
        int geoIndex = calculateGeologicIndex(x, y);
        return (geoIndex + depth) % 20183;
    }

    private static int calculateGeologicIndex(int x, int y) {
        if ((x == originX && y == originY) || (x == targetX && y == targetY)) {
            return 0;
        } else if (y == 0) {
            return x * 16807;
        } else if (x == 0) {
            return y * 48271;
        } else {
            int lastHorizontalRegionErosionLevel = erosionLevels.get(formattedPoint(x - 1, y));
            int lastVerticalRegionErosionLevel = erosionLevels.get(formattedPoint(x, y - 1));
            return lastHorizontalRegionErosionLevel * lastVerticalRegionErosionLevel;
        }
    }
}
