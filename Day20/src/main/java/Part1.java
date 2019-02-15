import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    private static ArrayList<String> directions = new ArrayList<>();
    private static LinkedHashMap<String, Integer> pointsAndDistances = new LinkedHashMap<>();

    public static void main (String[] args) throws IOException {
        directions.addAll(Arrays.asList(Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day20\\src\\main\\resources\\input.txt")).get(0).split("")));
        directions.remove(0);
        directions.remove(directions.size() - 1);
        pointsAndDistances.put(String.format("%s,%s", 0, 0), 0);
        followPath();
        ArrayList<Integer> distances = new ArrayList<>(pointsAndDistances.values());
        Collections.sort(distances);
        int furthestDistance = distances.get(distances.size() - 1);
        System.out.println("Furthest distance: " + furthestDistance);
        System.out.println("Done!");
    }

    private static void followPath() {
        LinkedList<PropertyBag> propertyBags = new LinkedList<>();

        int x = 0;
        int y = 0;
        int distance = 0;
        int index = 0;

        while (index < directions.size()) {
            String direction = directions.get(index);
            switch (direction) {
                case "N":
                    y--;
                    break;
                case "S":
                    y++;
                    break;
                case "E":
                    x++;
                    break;
                case "W":
                    x--;
                    break;
                case "(":
                    System.out.println("Found a left paren at index: " + index);
                    PropertyBag newPropertyBag = new PropertyBag(x, y, distance);
                    propertyBags.push(newPropertyBag);
                    index++;
                    continue;
                case "|":
                    System.out.println("Found a pipe at index: " + index);
                    PropertyBag mostRecentLeftParen = propertyBags.peek();
                    if (mostRecentLeftParen == null) {
                        throw new RuntimeException ("Eek!");
                    }
                    x = mostRecentLeftParen.x;
                    y = mostRecentLeftParen.y;
                    distance = mostRecentLeftParen.distance;
                    index++;
                    System.out.println("Moving to index: " + index);
                    continue;
                case ")":
                    System.out.println("Found a right paren at index: " + index);
                    mostRecentLeftParen = propertyBags.pop();
                    x = mostRecentLeftParen.x;
                    y = mostRecentLeftParen.y;
                    distance = mostRecentLeftParen.distance;
                    index++;
                    System.out.println("Moving to index: " + index);
                    continue;
            }
            distance++;
            String point = String.format("%s,%s", x, y);
            if (pointsAndDistances.keySet().contains(point)) {
                if (distance > pointsAndDistances.get(point)) {
                    index++;
                    continue;
                }
            }
            pointsAndDistances.put(point, distance);
            index++;
        }
    }

}
