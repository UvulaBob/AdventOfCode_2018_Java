import java.util.*;

// This doesn't work. I should try this day again.
public class Part1 {
    private static final int[] parameters = new int[]{6, 770, 4845};
    private static final int originX = 0;
    private static final int originY = 0;
    private static final int targetX = parameters[0];
    private static final int targetY = parameters[1];
    private static int searchAreaX = targetX * 2;
    private static int searchAreaY = targetY * 2;
    private static LinkedHashMap<String, Integer> erosionLevels = new LinkedHashMap<>();
    private static HashMap<String, HashMap<String, Integer>> worldMap = new HashMap<>();

    public static void main(String[] args) {
        for (int y = 0; y <= searchAreaY; y++) {
            for (int x = 0; x <= searchAreaX; x++) {
                erosionLevels.put(formattedPoint(x, y), calculateErosionLevel(x, y));
            }
        }

        HashMap<String, Integer> distances = new HashMap<>();

        for (String currentPoint : erosionLevels.keySet()) {
            int x = Integer.parseInt(currentPoint.split(",")[0]);
            int y = Integer.parseInt(currentPoint.split(",")[1]);
            ArrayList<String> validTools = getValidToolsForPoint(x, y);
            insertPath(formattedNode(x, y, validTools.get(0)), formattedNode(x, y, validTools.get(1)), 7);
            Point[] adjacentPoints = getAdjacentPoints(x, y);
            for (String tool : validTools) {
                for (Point adjacentPoint : adjacentPoints) {
                    if (getValidToolsForPoint(adjacentPoint.x, adjacentPoint.y).contains(tool)){
                        insertPath(formattedNode(x, y, tool), formattedNode(adjacentPoint.x, adjacentPoint.y, tool), 1);
                    }
                }
            }
        }

        Queue<String> queue = new PriorityQueue<>(worldMap.size(), new CompareUsingWeight(distances));

        distances.put(formattedNode(originX, originY, "T"), 0);

        for (String node : worldMap.keySet()) {
            distances.putIfAbsent(node, Integer.MAX_VALUE);
        }

        HashSet<String> visited = new HashSet<>();

        queue.add(formattedNode(originX, originY, "T"));
        while (queue.size() > 0) {
            String currentNode = queue.poll();
            if (visited.contains(currentNode)) {
                continue;
            }

            System.out.println("Visiting node: " + currentNode);
            visited.add(currentNode);

            int currentNodeDistance = distances.get(currentNode);
            for (Map.Entry<String, Integer> entry : worldMap.get(currentNode).entrySet()) {
                String currentAdjacentNode = entry.getKey();
                System.out.println("Examining adjacent node: " + currentAdjacentNode);
                int weight = entry.getValue();
                int currentAdjacentNodeRecordedDistance = distances.get(currentAdjacentNode);
                System.out.println("Node Distance: " + currentAdjacentNodeRecordedDistance);
                int currentAdjacentNodePotentialNewDistance = currentNodeDistance + weight;
                System.out.println("Potential New Node Distance: " + currentAdjacentNodePotentialNewDistance);

                if (currentAdjacentNodePotentialNewDistance < currentAdjacentNodeRecordedDistance) {
                    System.out.println("Updating " + currentAdjacentNode + " distance to : " + currentAdjacentNodePotentialNewDistance);
                    distances.put(currentAdjacentNode, currentAdjacentNodePotentialNewDistance);
                    System.out.println("Adding " + currentAdjacentNode + " to queue");
                    queue.add(currentAdjacentNode);
                }
            }
        }

        System.out.println("Distance to target: " + distances.get(formattedNode(targetX, targetY, "T")));
        System.out.println("Done!");


    }

    private static void insertPath(String node1, String node2, int weight){
        worldMap.putIfAbsent(node1, new HashMap<>());
        worldMap.get(node1).put(node2, weight);

        worldMap.putIfAbsent(node2, new HashMap<>());
        worldMap.get(node2).put(node1, weight);
    }

    private static ArrayList<String> getValidToolsForPoint(int x, int y) {
        String[] validTools;
        switch (erosionLevels.get(formattedPoint(x, y)) % 3) {
            case 0:
                validTools = new String[] {"T", "C"};
                break;
            case 1:
                validTools = new String[] {"C", "N"};
                break;
            default:
                validTools = new String[] {"N", "T"};
        }
        return new ArrayList<>(Arrays.asList(validTools));
    }

    private static Point[] getAdjacentPoints(int x, int y) {
        ArrayList<Point> adjacentPoints = new ArrayList<>();
        if (x > 0) {
            adjacentPoints.add(new Point(x - 1 , y));
        }
        if (y > 0) {
            adjacentPoints.add(new Point(x, y - 1));
        }
        if (x < searchAreaX){
            adjacentPoints.add(new Point(x + 1, y));
        }
        if (y < searchAreaY){
            adjacentPoints.add(new Point(x, y + 1));
        }

        Point[] arr = new Point[adjacentPoints.size()];
        return adjacentPoints.toArray(arr);

    }

    private static String formattedPoint(int x, int y) {
        return String.format("%s,%s", x, y);
    }

    private static String formattedNode (int x, int y, String tool) {
        return String.format("%s,%s,%s", x, y, tool);
    }

    private static int calculateErosionLevel(int x, int y) {
        int depth = parameters[2];
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
