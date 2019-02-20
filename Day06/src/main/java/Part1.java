import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> importantPoints = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day6\\src\\main\\resources\\input.txt"));
        HashMap<String, Integer> numberOfClosestLocations = new HashMap<>();

        int highestX = 0;
        int highestY = 0;
        int lowestX = Integer.MAX_VALUE;
        int lowestY = Integer.MAX_VALUE;

        for (String importantPoint : importantPoints ) {
            String[] splitPoint = importantPoint.split(", ");
            int x = Integer.parseInt(splitPoint[0]);
            int y = Integer.parseInt(splitPoint[1]);

            highestX = (x > highestX) ? x : highestX;
            highestY = (y > highestY) ? y : highestY;
            lowestX = (x < lowestX) ? x : lowestX;
            lowestY = (y < lowestY) ? y : lowestY;
        }


        for (int x1 = lowestX; x1 <= highestX; x1++) {
            for (int y1 = lowestY; y1 <= highestY; y1++) {
                ArrayList<ImportantPoint> points = new ArrayList<>();
                String currentPoint = x1 + ", " + y1;
                System.out.println("Working on coordinates: " + currentPoint);
                for (String importantPoint : importantPoints) {

                    int x2 = Integer.parseInt(importantPoint.split(", ")[0]);
                    int y2 = Integer.parseInt(importantPoint.split(", ")[1]);
                    int distanceToImportantPoint = Math.abs(x1 - x2) + Math.abs(y1 - y2);

                    points.add(new ImportantPoint(importantPoint, distanceToImportantPoint));
                }

                Collections.sort(points);
                if (points.get(0).distance != points.get(1).distance) {
                    String importantPoint = points.get(0).point;
                    numberOfClosestLocations.putIfAbsent(importantPoint, 0);
                    numberOfClosestLocations.put(importantPoint, numberOfClosestLocations.get(importantPoint) + 1);
                }
            }
        }

        int mostNumberOfClosestLocations = 0;
        String pointWithMostClosestLocations = "";
        for (Map.Entry<String, Integer> entry : numberOfClosestLocations.entrySet()) {
            String point = entry.getKey();
            int x = Integer.parseInt(point.split(", ")[0]);
            int y = Integer.parseInt(point.split(", ")[1]);

            if (x == lowestX || x == highestX || y == lowestY || y == highestY) {
                continue;
            }

            if (entry.getValue() > mostNumberOfClosestLocations) {
                mostNumberOfClosestLocations = entry.getValue();
                pointWithMostClosestLocations = point;
            }
        }


        // The point we're looking for is (190, 298)
        System.out.println("Point with largest area: " + pointWithMostClosestLocations);
        System.out.println("Area: " + mostNumberOfClosestLocations);
        System.out.println("Done!");
    }
}