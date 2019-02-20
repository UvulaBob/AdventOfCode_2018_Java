import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day6\\src\\main\\resources\\input.txt"));
        HashMap<Integer, String> importantCoordinates = new HashMap<>();
        HashMap<Integer, Integer> importantPointClosenessCounts = new HashMap<>();

        int counter = 1;
        for (String line : lines){
            importantCoordinates.put(counter, line);
            importantPointClosenessCounts.put(counter, 0);
            counter++;
        }


        String firstCoordinates = importantCoordinates.get(1);
        int lowestX = Integer.parseInt(firstCoordinates.split(", ")[0]);
        int lowestY = Integer.parseInt(firstCoordinates.split(", ")[1]);
        int highestX = Integer.parseInt(firstCoordinates.split(", ")[0]);
        int highestY = Integer.parseInt(firstCoordinates.split(", ")[1]);

        for (String coordinate : importantCoordinates.values()) {
            int x = Integer.parseInt(coordinate.split(", ")[0]);
            int y = Integer.parseInt(coordinate.split(", ")[1]);

            if (x < lowestX) {
                lowestX = x;
            }

            if (y < lowestY) {
                lowestY = y;
            }

            if (x > highestX) {
                highestX = x;
            }

            if (y > highestY) {
                highestY = y;
            }

        }

        int safeRegionSize = 0;

        for (int x = lowestX; x <= highestX; x++) {
            for (int y = lowestY; y <= highestY; y++) {
                System.out.println("Working on coordinates: " + x + ", " + y);
                HashMap <Integer, Integer> pointsAndDistances = new HashMap<>();
                for (Map.Entry<Integer, String> entry : importantCoordinates.entrySet()) {
                    Integer point = entry.getKey();
                    String coordinates = entry.getValue();

                    int importantX = Integer.parseInt(coordinates.split(", ")[0]);
                    int importantY = Integer.parseInt(coordinates.split(", ")[1]);

                    int distance = Math.abs(x - importantX) + Math.abs(y - importantY);

                    pointsAndDistances.put(point, distance);
                }

                int totalDistance = 0;
                for (int value : pointsAndDistances.values()) {
                    totalDistance = totalDistance + value;
                }

                if (totalDistance < 10000){
                    safeRegionSize++;
                }

                pointsAndDistances = sortHashMapByValues(pointsAndDistances);

                if (pointsAndDistances.values().toArray()[0] != pointsAndDistances.values().toArray()[1]) {
                    int point = (Integer) pointsAndDistances.keySet().toArray()[0];
                    int importantPointClosenessCount = importantPointClosenessCounts.get(point);
                    importantPointClosenessCounts.put(point, importantPointClosenessCount + 1);
                }
            }
        }

        HashMap<Integer, Integer> sortedImportantPointClosenessCounts = sortHashMapByValues(importantPointClosenessCounts);
        System.out.println((sortedImportantPointClosenessCounts.toString()));

        System.out.println("Safe Region Size: " + safeRegionSize);

    }

    public static LinkedHashMap<Integer, Integer> sortHashMapByValues(
            HashMap<Integer, Integer> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}